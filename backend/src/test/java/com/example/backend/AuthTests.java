package com.example.backend;

import com.example.backend.DTO.Auth.AuthenticationResponse;
import com.example.backend.DTO.Auth.LoginRequest;
import com.example.backend.DTO.Auth.RegisterRequest;
import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthenticationService;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * This class is used to test the authentication of the backend.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthTests {


    private static final String username = "testUser";
    private static final String password = "testPassword";
    private static final String WrongPassword = "wrongPassword";

    private static final RegisterRequest registerRequest =
            RegisterRequest.builder()
                    .username(username)
                    .password(password)
                    .email("testEmail@email.com")
                    .build();


    @SpyBean
    private JavaMailSender mailSender;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;
    @Test
    void RegisterUser() {
        doNothing().when(mailSender).send(any(MimeMessage.class));
        AuthenticationResponse response = authenticationService.register(registerRequest);
        assert(response.getStatus().is2xxSuccessful());
        // verify if the user is created
        assert userRepository.findByUsername(username).isPresent();
    }

    @Test
    void RegisterLogin() {
        doNothing().when(mailSender).send(any(MimeMessage.class));
        AuthenticationResponse response1 = authenticationService.register(registerRequest);
        assert(response1.getStatus().is2xxSuccessful());
        // verify if the user is created
        assert userRepository.findByUsername(username).isPresent();
        UserModel user = userRepository.findByUsername(username).get();
        user.setEnabled(true);
        userRepository.save(user);
        AuthenticationResponse response = authenticationService.login(LoginRequest.builder().username(username).password(password).build());
        assert (response.getStatus().is2xxSuccessful());
        assert (response.getAccessToken() != null);

    }
    @Test
    void RegisterLoginUnconfirmed() {
        doNothing().when(mailSender).send(any(MimeMessage.class));
        AuthenticationResponse response1 = authenticationService.register(registerRequest);
        assert(response1.getStatus().is2xxSuccessful());
        // verify if the user is created
        assert userRepository.findByUsername(username).isPresent();
        AuthenticationResponse response = authenticationService.login(LoginRequest.builder().username(username).password(password).build());
        assert (response.getStatus().is4xxClientError());
        assert (response.getAccessToken() == null);

    }
    @Test
    void RegisterLoginWrongPassword() {
        doNothing().when(mailSender).send(any(MimeMessage.class));
        authenticationService.register(registerRequest);
        AuthenticationResponse response = authenticationService.login(LoginRequest.builder().username(username).password(WrongPassword).build());
        assert (response.getStatus().is4xxClientError());
        assert (response.getAccessToken() == null);

    }

}
