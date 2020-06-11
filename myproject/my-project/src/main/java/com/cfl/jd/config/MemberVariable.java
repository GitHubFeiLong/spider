package com.cfl.jd.config;

import com.cfl.jd.config.ApplicationValue;
import com.cfl.jd.util.EmailUtil;
import com.cfl.jd.util.RedisUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName MemberVariable
 * @Author msi
 * @Date 2020/6/8 13:10
 * @Version 1.0
 */
@Component
public class MemberVariable {
    /**
     * 请求对象request
     */
    @Autowired
    public HttpServletRequest httpServletRequest;

    /**
     * session
     */
    @Autowired
    public HttpSession httpSession;

    /**
     *
     */
    @Autowired
    public JavaMailSenderImpl mailSender;

    /**
     * redis工具类
     */
    @Autowired
    public RedisUtil redisUtil;

    /**
     * rabbitMQ模板类对象
     */
    @Autowired
    public AmqpTemplate rabbitTemplate;

    /**
     * 在application.yml自定义的配置的对象
     */
    @Autowired
    public ApplicationValue applicationValue;

    /**
     * 邮件工具类
     */
    @Autowired
    public EmailUtil emailUtil;

}
