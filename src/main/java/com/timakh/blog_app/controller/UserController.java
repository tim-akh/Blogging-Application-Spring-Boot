package com.timakh.blog_app.controller;

import com.timakh.blog_app.dto.UserDto;
import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.mapper.UserMapper;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(
//                userService.getAllUsers()
//                        .stream()
//                        .map(userMapper::userToUserDto)
//                        .collect(Collectors.toList()),
                userMapper.userListToUserDtoList(userService.getAllUsers()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(
                userMapper.userToUserDto(userService.saveUser(userMapper.userDtoToUser(userDto))),
                HttpStatus.OK
        );
    }

//    @GetMapping("{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
//        return new ResponseEntity<>(userMapper.userToUserDto(userService.getUserById(id)), HttpStatus.OK);
//    }
    @GetMapping("{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) throws ResourceNotFoundException {
        return new ResponseEntity<>(userMapper.userToUserDto(userService.getUserByUsername(username)), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userService.getUserById(id);

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return new ResponseEntity<>(userMapper.userToUserDto(userService.saveUser(user)), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return new ResponseEntity<>(userMapper.userToUserDto(user), HttpStatus.OK);
    }
}