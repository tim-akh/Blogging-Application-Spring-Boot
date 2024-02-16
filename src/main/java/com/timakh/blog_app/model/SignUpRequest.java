package com.timakh.blog_app.model;

import lombok.Data;

@Data
public class SignUpRequest {

    private String email;
    private String username;
    private String password;
}
