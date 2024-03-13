package com.timakh.blog_app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntTest {

    private final String USERS_BASE_URL = "/api/v1/users";


    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private List<User> users;



    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        users.add(new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>()));
        users.add(new User(2L, "email2@ya.ru", "usr2", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>()));
        users.add(new User(3L, "email3@ya.ru", "usr3", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>()));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(users);

        mvc.perform(get(USERS_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()));
    }

    @Test
    @WithMockUser
    void shouldGetUserByUsername() throws Exception {
        final String username = "usr1";
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userService.getUserByUsername(username)).thenReturn(user);

        mvc.perform(get(USERS_BASE_URL + "/" + username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(user.getId()))
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(jsonPath("role").value(user.getRole().name()))
                .andExpect(jsonPath("banned").value(user.isBanned()))
                .andExpect(jsonPath("publications").value(user.getPublications()));
    }

    @Test
    @WithMockUser
    void shouldReturn404WhenGettingUserByNonExistingUsername() throws Exception {
        final String username = "unknown";

        when(userService.getUserByUsername(username)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(get(USERS_BASE_URL + "/" + username))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldSaveUser() throws Exception {

        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userService.saveUser(any(User.class))).thenAnswer((invocation) -> invocation.getArgument(0));

        mvc.perform(post(USERS_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("email").value(user.getEmail()))
                    .andExpect(jsonPath("username").value(user.getUsername()))
                    .andExpect(jsonPath("role").value(user.getRole().name()))
                    .andExpect(jsonPath("banned").value(user.isBanned()))
                    .andExpect(jsonPath("publications").value(user.getPublications()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldUpdateUser() throws Exception {

        final Long id = 1L;
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userService.getUserById(id)).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenAnswer((invocation) -> invocation.getArgument(0));

        mvc.perform(put(USERS_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("email").value(user.getEmail()))
                    .andExpect(jsonPath("username").value(user.getUsername()))
                    .andExpect(jsonPath("role").value(user.getRole().name()))
                    .andExpect(jsonPath("banned").value(user.isBanned()))
                    .andExpect(jsonPath("publications").value(user.getPublications()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn404WhenUpdatingNonExistingUser() throws Exception {

        final Long id = 1L;
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userService.getUserById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(put(USERS_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldDeleteUser() throws Exception {
        final Long id = 1L;
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userService.getUserById(id)).thenReturn(user);
        doNothing().when(userService).deleteUser(user);

        mvc.perform(delete(USERS_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(jsonPath("username").value(user.getUsername()))
                .andExpect(jsonPath("role").value(user.getRole().name()))
                .andExpect(jsonPath("banned").value(user.isBanned()))
                .andExpect(jsonPath("publications").value(user.getPublications()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn404WhenDeletingNonExistingUser() throws Exception {
        final Long id = 1L;

        when(userService.getUserById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(delete(USERS_BASE_URL + "/" + id))
                .andExpect(status().isNotFound());
    }

}
