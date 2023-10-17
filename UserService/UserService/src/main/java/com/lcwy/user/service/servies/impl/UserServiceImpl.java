package com.lcwy.user.service.servies.impl;

import com.lcwy.user.service.entities.User;
import com.lcwy.user.service.exceptions.ResourceNotFoundException;
import com.lcwy.user.service.repositories.UserRepository;
import com.lcwy.user.service.servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " is not found on server."));
    }
}
