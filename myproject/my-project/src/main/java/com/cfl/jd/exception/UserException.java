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
        super.code = userExceptionEnum.getCode();
        super.message = userExceptionEnum.getMessage();

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
