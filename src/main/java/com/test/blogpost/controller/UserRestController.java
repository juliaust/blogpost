package com.test.blogpost.controller;

import com.test.blogpost.dto.UserDTO;
import com.test.blogpost.entity.User;
import com.test.blogpost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    ResponseEntity<User> createUser(@RequestBody UserDTO body) {
        User user = new User();
        user.setName(body.getName());
        user.setLocalId(body.getLocalId());
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
