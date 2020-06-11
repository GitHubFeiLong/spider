package com.cfl.jd.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 * 装载application.yml文件中的自定义配置
 * @ClassName ApplicationValue
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:28
 * @Version 1.0
 */
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
     * 验证码过期时间
     */
    @Value("${customize.CaptchaExpiredTime}")
    public Integer CaptchaExpiredTime;


    /**
     * 将异常发送到的通知邮箱
     */
    @Value("${customize.exception.email}")
    public String receiveEmail;


}
