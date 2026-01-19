//package org.example.articlesystem.service;
//
//import org.example.articlesystem.entity.User;
//import org.example.articlesystem.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Transactional
//public class UserService备份 {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    public boolean addUser(User user) {
//        try {
//            return userMapper.insertUser(user) > 0;
//        } catch (Exception e) {
//            throw new RuntimeException("添加用户失败: " + e.getMessage());
//        }
//    }
//
//    public boolean updateUser(User user) {
//        try {
//            return userMapper.updateUser(user) > 0;
//        } catch (Exception e) {
//            throw new RuntimeException("更新用户失败: " + e.getMessage());
//        }
//    }
//
//    public boolean deleteUser(Integer id) {
//        try {
//            return userMapper.deleteUserById(id) > 0;
//        } catch (Exception e) {
//            throw new RuntimeException("删除用户失败: " + e.getMessage());
//        }
//    }
//
//    public User getUserById(Integer id) {
//        return userMapper.selectUserById(id);
//    }
//
//    public User getUserByUsername(String username) {
//        return userMapper.selectUserByUsername(username);
//    }
//
//    public Map<String, Object> getUserList(Integer page, Integer size, String keyword) {
//        Map<String, Object> result = new HashMap<>();
//
//        if (page == null || page < 1) page = 1;
//        if (size == null || size < 1) size = 10;
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("keyword", keyword);
//        params.put("start", (page - 1) * size);
//        params.put("limit", size);
//
//        List<User> users = userMapper.selectUserList(params);
//        int total = userMapper.selectUserCount(params);
//
//        result.put("users", users);
//        result.put("total", total);
//        result.put("page", page);
//        result.put("size", size);
//        result.put("totalPages", (int) Math.ceil((double) total / size));
//
//        return result;
//    }
//
//    public boolean login(String username, String password) {
//        User user = userMapper.selectUserByUsername(username);
//        if (user == null) {
//            return false;
//        }
//        return user.getPassword().equals(password);
//    }
//}