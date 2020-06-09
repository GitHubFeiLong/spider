package com.cfl.jd.dao;

import com.cfl.jd.entity.UserDO;

public interface UserRegistDAO {

    /**
     * 根据邮箱查询是否存在账号
     * @param email
     * @return
     */
    UserDO selectUserByEmail(String email);

    /**
     * 插入用户基本信息
     * @param user
     * @return
     */
    int insertUser(UserDO user);

    /**
     * 根据输入的用户名/邮箱/电话  查询用户信息
     * @param loginUsername
     * @return
     */
    UserDO selectUserByNameOrEmailOrPhone(String loginUsername);
}
