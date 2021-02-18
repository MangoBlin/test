package com.hbw.springbootapplication.service;

import com.hbw.springbootapplication.entity.User;

public interface UserService {

    User selectUsername(String username);

    User selectPassword(User user);

    Integer updateUser(User user);

    Integer updatePassword(User user);
}
