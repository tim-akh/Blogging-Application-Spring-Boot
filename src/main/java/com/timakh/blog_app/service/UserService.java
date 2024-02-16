package com.timakh.blog_app.service;

import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User findUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id=" + id + " was not found"));
    }



    public String signUpUser(User user) {
        boolean userExists = userRepository.findUserByEmail(user.getEmail()).isPresent();
        if (userExists) throw new IllegalStateException(EMAIL_TAKEN_MSG);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return "Registration Worked";
    }

}
