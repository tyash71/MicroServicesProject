package com.lcwy.user.service.controllers;

import com.lcwy.user.service.entities.User;
import com.lcwy.user.service.servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        var userResponse = userService.saveUser(user);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
