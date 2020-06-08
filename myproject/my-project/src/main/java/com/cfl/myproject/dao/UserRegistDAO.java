package com.cfl.myproject.dao;

import com.cfl.myproject.entity.User;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRegistDAO {

    /**
     * 根据邮箱查询是否存在账号
     * @param email
     * @return
     */
    User selectUserByEmail(String email);

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
