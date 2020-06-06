package com.cfl.myproject.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2020-05-30 16:18:44
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -86622663040604873L;
    /**
    * 主键id
    */
    private Integer id;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 随机盐
    */
    private String salt;
    /**
    * 电话
    */
    private String phone;
    /**
    * 邮箱
    */
    private String email;

    /**
     * 0表示被删除
     */
    private Integer delete;

    /**
    * 数据的状态（0表示正常）
    */
    private Integer status;
    /**
    * 最后登录ip地址
    */
    private String ip;
    /**
    * 最后登录时间
    */
    private String lastLoginTime;
    /**
    * 创建时间
    */
    private String createTime;
    /**
    * 删除时间
    */
    private String deleteTime;
    /**
    * 更新时间
    */
    private String updateTime;




}