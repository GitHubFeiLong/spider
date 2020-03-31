package com.spider.pojo;

import lombok.Data;

/**用户表
 * @author jingjing
 */
@Data
public class ZzUser {
    /**
     * id
     */
    private Long id;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 用户名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;


}
