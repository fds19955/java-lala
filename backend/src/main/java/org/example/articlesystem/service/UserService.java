package org.example.articlesystem.service;

import org.example.articlesystem.common.Result;
import org.example.articlesystem.dto.UserRegisterRequest;
import org.example.articlesystem.entity.User;
import org.example.articlesystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 用户注册服务
     */
    @Transactional(rollbackFor = Exception.class) // 确保所有异常都回滚[1,5](@ref)
    public Result<String> register(UserRegisterRequest registerRequest) {
        try {
            // 1. 校验确认密码是否匹配
            if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                return Result.error("密码与确认密码不一致");
            }

            // 2. 检查用户名是否已存在[4](@ref)
            if (getUserByUsername(registerRequest.getUsername()) != null) {
                return Result.error("用户名已存在");
            }

            // 3. 密码加密（核心安全步骤）[6](@ref)
            String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

            // 4. 构建并保存用户实体
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setPassword(encryptedPassword);
            user.setRole("USER");
            user.setStatus(1);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());

            // 5. 调用现有的addUser方法执行插入操作
            boolean success = addUser(user);
            if (success) {
                return Result.success("用户注册成功");
            } else {
                return Result.error("用户注册失败");
            }
        } catch (Exception e) {
            // 异常传播出方法，事务会自动回滚[2](@ref)
            throw new RuntimeException("注册业务处理失败: " + e.getMessage(), e);
        }
    }

    // 以下是您原有的方法，保持不变
    public boolean addUser(User user) {
        try {
            return userMapper.insertUser(user) > 0;
        } catch (Exception e) {
            throw new RuntimeException("添加用户失败: " + e.getMessage());
        }
    }

    public boolean updateUser(User user) {
        try {
            return userMapper.updateUser(user) > 0;
        } catch (Exception e) {
            throw new RuntimeException("更新用户失败: " + e.getMessage());
        }
    }

    public boolean deleteUser(Integer id) {
        try {
            return userMapper.deleteUserById(id) > 0;
        } catch (Exception e) {
            throw new RuntimeException("删除用户失败: " + e.getMessage());
        }
    }

    public User getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    public User getUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public Map<String, Object> getUserList(Integer page, Integer size, String keyword) {
        Map<String, Object> result = new HashMap<>();
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;

        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("start", (page - 1) * size);
        params.put("limit", size);

        List<User> users = userMapper.selectUserList(params);
        int total = userMapper.selectUserCount(params);

        result.put("users", users);
        result.put("total", total);
        result.put("page", page);
        result.put("size", size);
        result.put("totalPages", (int) Math.ceil((double) total / size));

        return result;
    }

    public boolean login(String username, String password) {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }
}