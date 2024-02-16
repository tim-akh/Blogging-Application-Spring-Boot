package com.timakh.blog_app.controller;

import com.timakh.blog_app.model.SignUpRequest;
import com.timakh.blog_app.service.AuthService;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public String register(@RequestBody SignUpRequest request) {
        return authService.register(request);
    }
}
