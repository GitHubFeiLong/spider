package com.cfl.jd.exception;

/**
 * 类描述：
 * 自定义异常的基类，其它模块的异常继承进行扩展
 * @ClassName BaseException
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:41
 * @Version 1.0
 */
public class BaseException extends RuntimeException{

    private String code;
    private String message;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
