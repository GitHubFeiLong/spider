package com.cfl.jd.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 * 封装了Spring的SimpleMailMessage发送邮件
 * @ClassName EmailUtil
 * @Description TODO
 * @Author msi
 * @Date 2020/6/11 19:30
 * @Version 1.0
 */
@Component
public class EmailUtil {

    @Autowired
    private JavaMailSenderImpl mailSender;


    /**
     * 发送一封标准格式的邮件
     * @param sender 邮件服务器邮箱
     * @param receiver 接收者邮箱
     * @param topic 邮件主题
     * @param context 邮件内容
     * @param end 邮件结尾
     */
    public void sendSimpleEmail(String sender, String receiver, String topic, String context, String end){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(topic);	//设置邮件标题
        message.setText(context + "\n\n" + end);	//设置邮件正文
        message.setTo(receiver);	//设置收件人
        message.setFrom(sender);	//设置发件人
        mailSender.send(message);	//发送邮件
        message = null; // 回收资源
    }


}
