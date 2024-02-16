package com.timakh.blog_app.controller;

import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        return userService.findUserById(id);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.findUserById(id);

        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        return userService.saveUser(user);
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        userService.deleteUser(user);
        return user;
    }
}