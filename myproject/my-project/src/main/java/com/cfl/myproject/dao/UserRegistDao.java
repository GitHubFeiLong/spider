package com.cfl.myproject.dao;

import com.cfl.myproject.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistDao {
    /**
     * 插入用户基本信息
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 根据输入的用户名/邮箱/电话  查询用户信息
     * @param loginUsername
     * @return
     */
    User selectUserByNameOrEmailOrPhone(String loginUsername);
}
