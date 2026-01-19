package org.example.articlesystem.dto;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String confirmPassword; // 确认密码字段，User实体中不需要此字段
}