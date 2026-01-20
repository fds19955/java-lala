package org.example.articlesystem.service;

import org.example.articlesystem.common.Result;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.example.articlesystem.service.impl.JwtService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public Result<String> login(String username, String password) {
        try {
            // 1. 使用Spring Security进行认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 2. 认证成功后，从Authentication对象中获取UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 3. 生成JWT令牌（使用impl包中的JwtService）
            String token = jwtService.generateToken(userDetails.getUsername(), "USER");

            return Result.success("登录成功", token);

        } catch (Exception e) {
            return Result.error("用户名或密码错误");
        }
    }
}