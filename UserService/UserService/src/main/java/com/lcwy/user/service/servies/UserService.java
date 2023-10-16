package com.lcwy.user.service.servies;

import com.lcwy.user.service.entities.User;

import java.util.List;

public interface UserService {
    //User Operations

    //Create User
    User saveUser(User user);

    //Get All Users
    List<User> getAllUsers();

    //Get User By Id
    User getUser(String userId);

}
