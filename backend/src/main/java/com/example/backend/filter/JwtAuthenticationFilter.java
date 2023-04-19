package com.example.backend.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.example.backend.service.JwtService;

import io.jsonwebtoken.Claims;

@Component
@RequiredArgsConstructor
// validates the access token before any request that needs authorization
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        // authentication header should be of the form "Bearer {accessToken}"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // the rest of the string after the "Bearer" keyword
        final String accessToken = authHeader.substring(7);

        final String username = jwtService.extractClaim(accessToken, jwtService.getJwtSecret(), Claims::getSubject);

        // user is not authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // get user details from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // token is valid
            if (jwtService.validateAccessToken(accessToken, userDetails)) {

                // this is needed in order to update security context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource());

                // pass our new token to the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}