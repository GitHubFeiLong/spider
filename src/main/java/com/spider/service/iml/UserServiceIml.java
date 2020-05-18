package com.spider.service.iml;

import com.spider.dao.UserDao;
import com.spider.pojo.ZzUser;
import com.spider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIml implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public ZzUser getZzUserById(int id) {
        return userDao.selectUserById(id);
    }

    @Override
    public ZzUser getZzUserByLogin(String loginname) {
        return userDao.selectUserByLogin(loginname);
    }


}
