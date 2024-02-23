package com.timakh.blog_app.service;

import com.timakh.blog_app.dto.AuthResponse;
import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final static String EMAIL_TAKEN_MSG = "This email has already been taken";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id=" + id + " was not found"));
    }

    public AuthResponse signUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) throw new IllegalStateException(EMAIL_TAKEN_MSG);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return new AuthResponse(user.getId(), user.getUsername(), user.getRole());
    }


    public Optional<User> validUsernameAndPassword(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

}
