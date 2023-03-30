package com.example.backend.service;

<<<<<<< HEAD
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
=======
>>>>>>> ebbac47037a3ec7f996931ab97900c882f84b4cb
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.example.backend.DTO.Auth.LoginRequest;
import com.example.backend.DTO.Auth.RegisterRequest;
import com.example.backend.controller.AuthenticationResponse;
import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
=======
import com.example.backend.controller.AuthenticationResponse;
import com.example.backend.controller.LoginRequest;
import com.example.backend.controller.RegisterRequest;
import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
>>>>>>> ebbac47037a3ec7f996931ab97900c882f84b4cb

@Service
@RequiredArgsConstructor
public class AuthenticationService {
<<<<<<< HEAD
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        @Autowired
        private final JavaMailSender mailSender;

        private void sendVerificationEmail(UserModel user, String siteURL)
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
                String verifyURL = "http://" + siteURL + "/api/v1/auth/verify?code=" + user.getVerificationToken();

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

        public AuthenticationResponse register(RegisterRequest request, String siteURL) {
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
                        sendVerificationEmail(user, siteURL);
                } catch (UnsupportedEncodingException e) {
                        System.out.println("sendVerificationEmail UnsupportedEncodingException");
                        e.printStackTrace();
                } catch (MessagingException e) {
                        System.out.println("sendVerificationEmail MessagingException");
                        e.printStackTrace();
                }

                // generate and return token
                String jwt = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwt)
                                .build();
        }

        public AuthenticationResponse login(LoginRequest request) {
                // actual authentication
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));

                // find user in db
                UserModel user = userRepository.findByUsername(request.getUsername())
                                .orElseThrow();

                // generate and return token
                String jwt = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwt)
                                .build();
        }
=======
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // build user model
        UserModel user = UserModel.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        // save in db
        userRepository.save(user);

        // generate and return token
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        // actual authentication
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        // find user in db
        UserModel user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        // generate and return token
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
>>>>>>> ebbac47037a3ec7f996931ab97900c882f84b4cb

}
