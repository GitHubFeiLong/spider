package com.cfl.jd.enumerate;

/**
 * 用户登录的枚举类：包含三种情况
 *      user_password_match: 600 #用户名和密码匹配
 *      user_not_exist: 601  #用户不存在
 *      user_wrong_password: 602  #密码错误
 * @ClassName UserLoginEnum
 * @Author msi
 * @Date 2020/6/9 19:31
 * @Version 1.0
 */
public enum UserLoginEnum {
    USER_PASSWORD_MATCH("用户名和密码匹配",600),
    USER_NOT_EXIST("用户不存在",601),
    USER_WRONG_PASSWORD("密码错误",602);

    /**
     * 具体描述
     */
    private String message;

    /**
     * 状态码
     */
    private Integer code;

    UserLoginEnum(String message, Integer code){
        this.message = message;
        this.code = code;
    }

    public String getMessage(){
        return message;
    }
    public Integer getCode() {
        return code;
    }
}
