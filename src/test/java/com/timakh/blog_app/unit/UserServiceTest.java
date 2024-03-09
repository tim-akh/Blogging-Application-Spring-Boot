package com.timakh.blog_app.unit;

import com.timakh.blog_app.dto.AuthResponse;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.repository.UserRepository;
import com.timakh.blog_app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, passwordEncoder);
    }


    @Test
    void shouldGetAllUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>()));
        users.add(new User(2L, "email2@ya.ru", "usr2", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>()));
        users.add(new User(3L, "email3@ya.ru", "usr3", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>()));

        when(userRepository.findAll()).thenReturn(users);

        List<User> expected = underTest.getAllUsers();
        Assertions.assertEquals(users, expected);
    }

    @Test
    void shouldGetUserById() {
        final Long id = 1L;
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        final Optional<User> expectedOptional = Optional.ofNullable(underTest.getUserById(id));

        Assertions.assertNotNull(expectedOptional);
    }

    @Test
    void shouldGetUserByUsername() {
        final String username = "usr1";
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        final Optional<User> expectedOptional = Optional.ofNullable(underTest.getUserByUsername(username));

        Assertions.assertNotNull(expectedOptional);
    }

    @Test
    void shouldSaveUserSuccessfully() {
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userRepository.save(user)).thenReturn(user);

        final User savedUser = underTest.saveUser(user);

        Assertions.assertNotNull(savedUser);
        verify(userRepository).save(any(User.class));
    }


    @Test
    void shouldDeleteUser() {
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        underTest.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    void shouldSignUpUserSuccessfully() {
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        when(userRepository.save(user)).thenReturn(user);

        final AuthResponse savedUser = underTest.signUpUser(user);

        Assertions.assertNotNull(savedUser);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenSignUpUserWithExistingEmail() {
        final User user = new User(1L, "email1@ya.ru", "usr1", "pass", Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Assertions.assertThrows(IllegalStateException.class,() -> {
            underTest.signUpUser(user);
        });

        verify(userRepository, never()).save(any(User.class));
    }
}
