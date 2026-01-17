package org.example.articlesystem.controller;

import org.example.articlesystem.common.Result;
import org.example.articlesystem.dto.LoginRequest;
import org.example.articlesystem.dto.UserRegisterRequest;
import org.example.articlesystem.service.AuthService;
import org.example.articlesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        // 将认证逻辑委托给Service层
//        return authService.login(username, password);
        return authService.login(loginRequest.getUsername(), loginRequest.getPassword());

    }
    @PostMapping("/register")
    public Result<String> registerUser(@RequestBody UserRegisterRequest registerRequest) {
        // Controller专注于请求处理，业务逻辑委托给Service
        return userService.register(registerRequest);
    }
}