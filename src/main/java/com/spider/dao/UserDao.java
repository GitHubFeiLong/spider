package com.spider.dao;


import com.spider.pojo.ZzUser;

/**
 * @author jingjing
 */
public interface UserDao {
    /**
     * 根据id查找user
     * @param id
     * @return
     */
    ZzUser selectUserById(int id);

    /**
     * 根据登录名查找user
     * @param loginname
     * @return
     */
    ZzUser selectUserByLogin(String loginname);
}
