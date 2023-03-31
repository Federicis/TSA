package com.example.backend.service;

import java.io.UnsupportedEncodingException;

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
                                .enabled(false)
                                .verificationToken(randomToken)
                                .build();

                // save in db
                userRepository.save(user);

                // send verification email and handle exceptions
                try {
                        sendVerificationEmail(user);
                } catch (UnsupportedEncodingException | MessagingException e) {
                        System.out.println("Error while sending verification email.");
                        e.printStackTrace();
                }

                // return ok
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
