package org.example.articlesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan("org.example.articlesystem.mapper")
public class ArticleSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleSystemApplication.class, args);
        System.out.println("""
            ========================================
            文章管理系统启动成功!
            Spring Boot 版本: 4.0.1
            访问地址: http://localhost:8080
            ========================================
            """);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456"; // 您的明文密码
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("加密后的密码: " + encodedPassword);
    }

}
