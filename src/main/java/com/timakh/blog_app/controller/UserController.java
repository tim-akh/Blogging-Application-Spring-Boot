package com.timakh.blog_app.controller;

import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.getUserById(id);

        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}