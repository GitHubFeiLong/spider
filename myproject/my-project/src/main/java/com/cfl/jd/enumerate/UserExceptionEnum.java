package com.cfl.jd.enumerate;

/**
 * 枚举描述：
 * 用户模块的定义异常的枚举
 * @ClassName ExceptionEnum
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:45
 * @Version 1.0
 */
public enum UserExceptionEnum {

    /**
     * 状态码的格式：六位的错误码
     *
     */

    /********** 邮箱的异常 **********/
    // 邮箱是无效的
    MAILBOX_IS_INVALID("100101", "注册的邮箱不存在"),
    // 邮箱未注册
    MAILBOX_NOT_EXIST("100102", "邮箱不存在"),
    // 邮箱已被注册
    MAILBOX_IS_EXIST("100103", "账号已经被注册，请登录"),

    /********** 手机号的异常 **********/
    // 手机号是无效的
    PHONE_IS_INVALID("100201", "手机号无效"),
    // 手机号未注册
    PHONE_NOT_EXIST("100202", "手机号不存在"),
    // 手机号已被注册
    PHONE_IS_EXIST("100203", "手机号已经被注册，请登录"),

    /********** 用户名的异常 **********/
    // 用户名是无效的
    USERNAME_IS_INVALID("100301", "用户名无效"),
    // 用户名未注册
    USERNAME_NOT_EXIST("100302", "用户名不存在"),
    // 用户名已被注册
    USERNAME_IS_EXIST("100303", "用户名已经被注册，请登录"),

    /********** 注册时验证码 **********/
    CAPTCHA_MISMATCH("100401", "输入验证码与发送邮箱的验证码不匹配"),

    /********** 登陆时用户名和密码不匹配 **********/
    USER_WRONG_PASSWORD("100402","密码输入错误");



    private String code;
    private String message;

    UserExceptionEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
