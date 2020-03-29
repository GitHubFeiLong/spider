package com.spider.dao;


import com.spider.pojo.ZzUser;

public interface UserDao {
    ZzUser selectUserById(int id);
}
