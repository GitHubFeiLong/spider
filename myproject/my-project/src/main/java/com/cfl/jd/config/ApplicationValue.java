package com.cfl.jd.config;

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
     * 防止用户重复提交
     */
    @Value("${customize.uniqueToken}")
    public String uniqueToken;


}