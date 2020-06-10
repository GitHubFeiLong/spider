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

    /**
     * 错误代码
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;

    public UserException(UserExceptionEnum userExceptionEnum) {

        this("错误代码(" + userExceptionEnum.getCode() + ")->" + userExceptionEnum.getMessage());
        this.code = userExceptionEnum.getCode();
        this.message = userExceptionEnum.getMessage();

    }

    public UserException(String message) {
        super(message);
    }

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
