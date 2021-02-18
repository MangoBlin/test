package com.hbw.springbootapplication.mapper;

import com.hbw.springbootapplication.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User selectUsername(String username);

    User selectPassword(@Param(value = "user") User user);

    Integer updateUser(@Param(value = "tempUser")User user);

    Integer updatePassword(@Param(value = "tempUser")User user);
}
