package com.cfl.myproject.dao;

import com.cfl.myproject.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistDao {

    int insertUser(User user);
}
