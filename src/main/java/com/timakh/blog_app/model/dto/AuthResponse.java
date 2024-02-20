package com.timakh.blog_app.model.dto;

import com.timakh.blog_app.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private Role role;


}
