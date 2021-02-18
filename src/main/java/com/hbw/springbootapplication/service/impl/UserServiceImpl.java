package com.hbw.springbootapplication.service.impl;

import com.hbw.springbootapplication.entity.User;
import com.hbw.springbootapplication.mapper.UserMapper;
import com.hbw.springbootapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public User selectUsername(String username) {
        User user = userMapper.selectUsername(username);
        return user;
    }

    @Override
    public User selectPassword(User user) {
        return userMapper.selectPassword(user);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public Integer updatePassword(User user) {
        return userMapper.updatePassword(user);
    }
}
