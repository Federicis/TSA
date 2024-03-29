package com.example.backend.service;

import com.example.backend.DTO.Auth.AuthenticationResponse;
import com.example.backend.model.RefreshTokenModel;
import com.example.backend.model.UserModel;
import com.example.backend.model.enumeration.Role;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${tsa.jwtSecret}")
    private String JWT_SECRET;

    @Value("${tsa.refreshSecret}")
    private String REFRESH_SECRET;

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 10;
    private final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 30;

    public String getJwtSecret() {
        return JWT_SECRET;
    }

    public String getRefreshSecret() {
        return REFRESH_SECRET;
    }

    private Claims extractAllClaims(String token, String secret) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // extracts single claim from token
    public <T> T extractClaim(String token, String secret, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, secret);

        return claimsResolver.apply(claims);
    }

    public String extractUsernameFromAccessToken(String token) {
        return extractClaim(token, JWT_SECRET, Claims::getSubject);
    }

    public String getUsernameFromRequest(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        // authentication header should be of the form "Bearer {accessToken}"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authHeader.substring(7);

        return extractUsernameFromAccessToken(token);
    }

    public String getAccesToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        String authorizationHeader = requestAttributes.getRequest().getHeader("Authorization");
        return authorizationHeader.substring(7);
    }

    public String getCurrentUserUsername() {
        return extractUsernameFromAccessToken(getAccesToken());
    }

    public boolean isTokenExpired(String token, String secret) {
        return extractClaim(token, secret, Claims::getExpiration).before(new Date());
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            String secret,
            Long time) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(getSigningKey(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    // uses the other generateToken method to generate a token without extra claims
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, JWT_SECRET, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, REFRESH_SECRET, REFRESH_TOKEN_EXPIRATION);
    }

    public boolean validateToken(String token, UserDetails userDetails, String secret) {
        final String username = extractClaim(token, secret, Claims::getSubject);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, secret));
    }

    public boolean validateAccessToken(String token, UserDetails userDetails) {
        return validateToken(token, userDetails, JWT_SECRET);
    }

    public boolean validateRefreshToken(String token, UserDetails userDetails) {
        return validateToken(token, userDetails, REFRESH_SECRET);
    }

    // creates a secret key based on the SECRET_KEY string
    private Key getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public RefreshTokenModel createRefreshToken(UserModel user) {
        return RefreshTokenModel.builder()
                .user(user)
                .token(generateRefreshToken(user))
                .build();
    }

    public AuthenticationResponse refreshToken(String token) {
        RefreshTokenModel tokenModel = refreshTokenRepository.findByToken(token).orElse(null);

        // token not found in db
        if (tokenModel == null) {
            return AuthenticationResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        // invalid token => delete from db and return unauthorized
        if (!validateRefreshToken(tokenModel.getToken(), tokenModel.getUser())) {
            refreshTokenRepository.delete(tokenModel);
            return AuthenticationResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        // create new access token and return both tokens
        String newAccessToken = generateAccessToken(tokenModel.getUser());

        return AuthenticationResponse.builder()
                .status(HttpStatus.OK)
                .refreshToken(tokenModel.getToken())
                .accessToken(newAccessToken)
                .build();
    }

    public boolean isAdmin() {
        UserModel user = userRepository.findByUsername(getCurrentUserUsername()).orElseThrow(
                () -> new IllegalStateException("User not found"));
        return user.getRole().equals(Role.ADMIN);
    }
}
