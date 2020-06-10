package com.cfl.jd.enumerate;

/**
 *
 *
 * @ClassName UserLoginEnum
 * @Author msi
 * @Date 2020/6/9 19:31
 * @Version 1.0
 */
/**
 * 类描述：
 * 用户登录的枚举类：包含三种情况
 * @ClassName UserLoginEnum
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:37
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

    /**
     * 构造器
     * @param message
     * @param code
     */
    UserLoginEnum(String message, Integer code){
        this.message = message;
        this.code = code;
    }

    /**
     * 获取信息
     * @return
     */
    public String getMessage(){
        return message;
    }

    /**
     * 获取状态码
     * @return
     */
    public Integer getCode() {
        return code;
    }
}
