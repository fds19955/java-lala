package org.example.articlesystem.controller;

import org.example.articlesystem.common.Result;
import org.example.articlesystem.dto.UserRegisterRequest;
import org.example.articlesystem.entity.User;
import org.example.articlesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<Map<String, Object>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {

        try {
            Map<String, Object> result = userService.getUserList(page, size, keyword);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取用户列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<User> getUserDetail(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return Result.error(404, "用户不存在");
            }
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("获取用户详情失败: " + e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public Result<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return Result.error(404, "用户不存在");
            }
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("获取用户失败: " + e.getMessage());
        }
    }

    @PostMapping
    public Result<String> createUser(@RequestBody User user) {
        try {
            user.setStatus(1);

            boolean success = userService.addUser(user);
            if (success) {
                return Result.success("用户创建成功");
            } else {
                return Result.error("用户创建失败");
            }
        } catch (Exception e) {
            return Result.error("创建用户失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Integer id, @RequestBody User user) {
        try {
            user.setId(id);
            boolean success = userService.updateUser(user);
            if (success) {
                return Result.success("用户更新成功");
            } else {
                return Result.error("用户更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新用户失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Integer id) {
        try {
            boolean success = userService.deleteUser(id);
            if (success) {
                return Result.success("用户删除成功");
            } else {
                return Result.error("用户删除失败");
            }
        } catch (Exception e) {
            return Result.error("删除用户失败: " + e.getMessage());
        }
    }
}