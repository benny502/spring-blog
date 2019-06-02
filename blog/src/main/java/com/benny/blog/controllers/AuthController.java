package com.benny.blog.controllers;

import java.util.HashMap;
import java.util.Map;

import com.benny.blog.entity.User;
import com.benny.blog.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    protected UserRepository repository;

    @RequestMapping("/testCreateUser")
    public String test() {
        User user = new User();
        user.email = "benny502@126.com";
        user.username = "benny502";
        user.password = "123456";
        this.repository.save(user);
        return "ok";
    }

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        Map<String, Object> result = new HashMap<String, Object>();
        // List<Long> set = this.repository.findUserByEmailAndPassword(email, password);
        // if(set.isEmpty()){
        //     result.put("ret", 0);
        //     result.put("msg", "登陆失败，用户名与密码不正确");
        // }else {
        //     result.put("user", set);
        // }
        return result;
    }
}