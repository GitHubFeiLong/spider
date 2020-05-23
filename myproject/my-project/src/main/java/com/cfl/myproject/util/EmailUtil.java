package com.cfl.myproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    public static final String EMAIL_SUBJECT = "";
    public static final String EMAIL_TITLE = "";

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送邮件
     * @param sender 发送者
     * @param receiver 接收者
     * @param subject 主题
     * @param text 内容
     */
    public void sendSimpleEmail(String sender, String receiver, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);	//设置邮件标题
        message.setText(text);	//设置邮件正文
        message.setTo(receiver);	//设置收件人
        message.setFrom(sender);	//设置发件人
        mailSender.send(message);	//发送邮件
        message = null;
    }
}
