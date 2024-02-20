package com.timakh.blog_app.controller;

import com.timakh.blog_app.model.dto.AuthResponse;
import com.timakh.blog_app.model.dto.LoginRequest;
import com.timakh.blog_app.model.dto.SignUpRequest;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.AuthService;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn( @RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userService.validUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new ResponseEntity<>(new AuthResponse(user.getId(), user.getUsername(), user.getRole()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpRequest request) {
        return authService.register(request);
    }
}
