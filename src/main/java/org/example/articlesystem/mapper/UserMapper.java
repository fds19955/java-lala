package org.example.articlesystem.mapper;

import org.example.articlesystem.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    int deleteUserById(Integer id);

    int updateUser(User user);

    User selectUserById(Integer id);

    User selectUserByUsername(String username);

    List<User> selectUserList(Map<String, Object> params);

    int selectUserCount(Map<String, Object> params);
}