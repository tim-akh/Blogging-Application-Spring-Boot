package com.timakh.blog_app.controller;

import com.timakh.blog_app.dto.AuthResponse;
import com.timakh.blog_app.dto.LoginRequest;
import com.timakh.blog_app.dto.SignUpRequest;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.AuthService;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn( @RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userService.validUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getBanned()) {
                return new ResponseEntity<>(new AuthResponse(user.getId(), user.getUsername(), user.getRole()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignUpRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.OK);
    }

}
