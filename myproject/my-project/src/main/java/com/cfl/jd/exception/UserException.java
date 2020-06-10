package com.cfl.jd.exception;

import com.cfl.jd.enumerate.UserExceptionEnum;

/**
 * 类描述：
 * 用户相关业务的异常类
 * @ClassName UserException
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 21:06
 * @Version 1.0
 */
public class UserException extends BaseException{

    public UserException(String message) {
        super(message);
    }

    /**
     * 用户模块的异常枚举类，定义了异常码和异常信息
     * @param userExceptionEnum 用户模块的异常枚举类
     */
    public UserException(UserExceptionEnum userExceptionEnum) {
        this("错误代码(" + userExceptionEnum.getCode() + ")->" + userExceptionEnum.getMessage());
        this.code = userExceptionEnum.getCode();
        this.message = userExceptionEnum.getMessage();

    }

    /**
     * 指定异常的状态码和异常信息
     * @param code 异常码
     * @param message 异常详细信息
     */
    public UserException(String code, String message) {
        this("错误代码(" + code + ")->" + message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
