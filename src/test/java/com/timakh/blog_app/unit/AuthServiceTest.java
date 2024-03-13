package com.timakh.blog_app.unit;

import com.timakh.blog_app.dto.AuthResponse;
import com.timakh.blog_app.dto.SignUpRequest;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.AuthService;
import com.timakh.blog_app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserService userService;

    private AuthService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthService(userService);
    }

    @Test
    void shouldRegisterUser() {

        final User user = new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false);

        final AuthResponse response = new AuthResponse(user.getId(), user.getUsername(), user.getRole());

        final SignUpRequest request = new SignUpRequest(user.getEmail(), user.getUsername(), user.getPassword());

        when(userService.signUpUser(user)).thenReturn(response);

        final AuthResponse expectedResponse = underTest.register(request);

        Assertions.assertEquals(expectedResponse, response);

    }

}
