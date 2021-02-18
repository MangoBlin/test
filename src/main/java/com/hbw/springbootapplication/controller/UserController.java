package com.hbw.springbootapplication.controller;

import com.hbw.springbootapplication.entity.User;
import com.hbw.springbootapplication.entity.UserPage;
import com.hbw.springbootapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/selectUser")
    public @ResponseBody UserPage selectUser(@RequestBody User user) {
        UserPage userPage = new UserPage();

        User user1 = userService.selectUsername(user.getUsername());
        if (user1 == null) {
            userPage.setCode(-1);
            userPage.setMsg("用户名不存在");
        } else {
            User user2 = userService.selectPassword(user);
            if (user2 == null) {
                userPage.setCode(-2);
                userPage.setMsg("密码错误");
            } else {
                userPage.setUser(user);
                userPage.setMsg("success");
                userPage.setCode(1);
            }
        }
        return userPage;
    }

    @RequestMapping("/selectByUsername")
    public @ResponseBody
    User selectByUsername(@RequestBody String username) {
        return userService.selectUsername(username);
    }

    @RequestMapping("/updateUser")
    public @ResponseBody
    Integer updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @RequestMapping("/updatePassword")
    public @ResponseBody
    Integer updatePassword(@RequestBody User user) {
        return userService.updatePassword(user);
    }

    @RequestMapping("/setCookie")
    public void setCookies(HttpServletResponse response,String username) {
        Cookie cookie = new Cookie("username", username);
        response.addCookie(cookie);
    }

    @RequestMapping(value = "/getCookies", method = RequestMethod.GET)
    public UserPage getCookies(HttpServletRequest request) {
        UserPage userPage = new UserPage();
        User user = new User();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {

            userPage.setCode(1);
            user.setUsername(cookies[0].getValue());
            userPage.setUser(user);
        }else {
            userPage.setUser(user);
        }
        return userPage;
    }


    @RequestMapping("/testCookieValue")
    public @ResponseBody UserPage testCookieValue(@CookieValue("username") String sessionId) {
        UserPage userPage = new UserPage();
        User user = new User();
        userPage.setCode(1);
        user.setUsername(sessionId);
        userPage.setUser(user);
        return userPage;
    }
}