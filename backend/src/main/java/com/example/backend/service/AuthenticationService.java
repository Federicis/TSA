package com.example.backend.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.Auth.AuthenticationResponse;
import com.example.backend.DTO.Auth.LoginRequest;
import com.example.backend.DTO.Auth.RegisterRequest;
import com.example.backend.model.RefreshTokenModel;
import com.example.backend.model.UserModel;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;

import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository userRepository;
        private final RefreshTokenRepository refreshTokenRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        @Autowired
        private final JavaMailSender mailSender;

        @Value("${tsa.siteUrl}")
        private String siteURL;

        private void sendVerificationEmail(UserModel user)
                        throws MessagingException, UnsupportedEncodingException {

                String toAddress = user.getEmail();
                String fromAddress = "tsa.mailer0@gmail.com";
                String senderName = "TSA Mailer";
                String subject = "Please verify your account";
                String content = "Dear [[name]],<br>"
                                + "Please click the link below to verify your registration:<br>"
                                + "<h3><a href='[[URL]]' target='_blank'>VERIFY</a></h3><br>"
                                + "Thank you,<br>"
                                + "TSA.";

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setFrom(fromAddress, senderName);
                helper.setTo(toAddress);
                helper.setSubject(subject);

                content = content.replace("[[name]]", user.getUsername());
                String verifyURL = siteURL + "/api/v1/auth/verify?code=" + user.getVerificationToken();

                content = content.replace("[[URL]]", verifyURL);

                helper.setText(content, true);

                mailSender.send(message);
        }

        public boolean verifyUser(String verificationToken) {
                UserModel user = userRepository.findByVerificationToken(verificationToken).orElse(null);
                if (user == null || user.isEnabled()) {
                        return false;
                }

                // enable user
                user.setEnabled(true);
                userRepository.save(user);

                return true;

        }

        public AuthenticationResponse register(RegisterRequest request) {
                // verify that the username and email are unique
                UserModel userByUsername = userRepository.findByUsername(request.getUsername()).orElse(null);
                UserModel userByEmail = userRepository.findByEmail(request.getEmail()).orElse(null);

                // user already exists => conflict
                if (userByEmail != null || userByUsername != null) {
                        return AuthenticationResponse.builder()
                                        .status(HttpStatus.CONFLICT)
                                        .build();
                }

                // generate random string for the verification token
                String randomToken = RandomString.make(64);

                // build user model
                UserModel user = UserModel.builder()
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role("USER")
                                .enabled(true) // ONLY FOR TESTING
                                .verificationToken(randomToken)
                                .build();
                userRepository.save(user);


            // send verification email and handle exceptions
//                try {
//                        sendVerificationEmail(user);
//
//                        // save in db
//                        userRepository.save(user);
//
//                        // return ok
//                        return AuthenticationResponse.builder()
//                                        .status(HttpStatus.OK)
//                                        .build();
//                } catch (UnsupportedEncodingException | MessagingException e) {
//                        System.out.println("Error while sending verification email.");
//                        e.printStackTrace();
//
//                        return AuthenticationResponse.builder()
//                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                        .build();
//                }
            return AuthenticationResponse.builder()
                                        .status(HttpStatus.OK)
                                        .build();
        }

        public AuthenticationResponse login(LoginRequest request) {
                // actual authentication
                try {
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getUsername(),
                                                        request.getPassword()));

                } catch (DisabledException | LockedException e) {
                        // account is locked / disabled => forbidden
                        return AuthenticationResponse.builder()
                                        .status(HttpStatus.FORBIDDEN)
                                        .build();

                } catch (BadCredentialsException e) {
                        // bad credentials => unauthorized
                        return AuthenticationResponse.builder()
                                        .status(HttpStatus.UNAUTHORIZED)
                                        .build();
                }

                // find user in db
                UserModel user = userRepository.findByUsername(request.getUsername()).orElseThrow();

                // remove invalid tokens
                List<RefreshTokenModel> userRefreshTokens = refreshTokenRepository.findAllByUser(user);
                List<RefreshTokenModel> validRefreshTokens = new ArrayList<>();

                for (int idx = 0; idx < userRefreshTokens.size(); idx++) {
                        RefreshTokenModel currToken = userRefreshTokens.get(idx);
                        if (jwtService.validateRefreshToken(currToken.getToken(), user)) {
                                validRefreshTokens.add(currToken);
                        } else {
                                refreshTokenRepository.delete(currToken);
                        }
                }

                // sort tokens by expriation date
                Collections.sort(validRefreshTokens, new Comparator<RefreshTokenModel>() {
                        public int compare(RefreshTokenModel token1, RefreshTokenModel token2) {
                                Date expiration1 = jwtService.extractClaim(token1.getToken(),
                                                jwtService.getRefreshSecret(), Claims::getExpiration);
                                Date expiration2 = jwtService.extractClaim(token2.getToken(),
                                                jwtService.getRefreshSecret(), Claims::getExpiration);
                                return expiration2.compareTo(expiration1);
                        }
                });

                // limit number of active tokens per user to 2
                // by removing the oldest one if there are already 2
                while (validRefreshTokens.size() >= 2) {
                        int lastIdx = validRefreshTokens.size() - 1;

                        refreshTokenRepository.delete(validRefreshTokens.get(lastIdx));
                        validRefreshTokens.remove(lastIdx);
                }

                // generate and return tokens
                String accessToken = jwtService.generateAccessToken(user);
                RefreshTokenModel refreshToken = jwtService.createRefreshToken(user);

                refreshTokenRepository.save(refreshToken);

                return AuthenticationResponse.builder()
                                .status(HttpStatus.OK)
                                .accessToken(accessToken)
                                .refreshToken(refreshToken.getToken())
                                .build();
        }

}
