package com.timakh.blog_app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timakh.blog_app.dto.AuthResponse;
import com.timakh.blog_app.dto.SignUpRequest;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntTest {

    private final String AUTH_BASE_URL = "/api/v1/auth";

    @MockBean
    private AuthService authService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser
    void shouldSignUp() throws Exception {

        final AuthResponse response = new AuthResponse(1L, "usr1", Role.ROLE_USER);

        when(authService.register(any(SignUpRequest.class))).thenReturn(response);

        mvc.perform(post(AUTH_BASE_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(response)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(response.getId()))
                    .andExpect(jsonPath("username").value(response.getUsername()))
                    .andExpect(jsonPath("role").value(response.getRole().name()));

    }


}
