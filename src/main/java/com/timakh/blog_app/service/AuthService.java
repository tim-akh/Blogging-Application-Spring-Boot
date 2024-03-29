package com.timakh.blog_app.service;

import com.timakh.blog_app.dto.AuthResponse;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.dto.SignUpRequest;
import com.timakh.blog_app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;

    public AuthResponse register(SignUpRequest request) {
        return userService.signUpUser(new User(
                request.getEmail(),
                request.getUsername(),
                request.getPassword(),
                Role.ROLE_USER,
                false
        ));
    }

}
