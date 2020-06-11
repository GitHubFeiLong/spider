package com.cfl.jd.dao;

import com.cfl.jd.entity.UserDO;

/**
 * 类描述：
 * 用户的DAO层
 * @ClassName UserDAO
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:35
 * @Version 1.0
 */
public interface UserDAO {

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
