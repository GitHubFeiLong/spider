package com.cfl.myproject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationValue {

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    public String applicationName;

    /**
     * 发送邮件账号
     */
    @Value("${spring.mail.username}")
    public String senderEmail;


    /*******自定义参数*******/

    /**
     * 将异常发送到的通知邮箱
     */
    @Value("${customize.exception.email}")
    public String receiveEmail;


    /**
     * 用户密码匹配状态码
     */
    @Value("${customize.exception.code.user_password_match}")
    public String userPasswordMatch;

    /**
     * 用户不存在code类似响应码
     */
    @Value("${customize.exception.code.user_not_exist}")
    public String userNotExist;

    /**
     * 密码错误状态码
     */
    @Value("${customize.exception.code.user_wrong_password}")
    public String userWrongPassword;
}
