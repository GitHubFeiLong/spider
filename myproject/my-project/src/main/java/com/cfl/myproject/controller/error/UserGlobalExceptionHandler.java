package com.cfl.myproject.controller.error;


import com.cfl.myproject.config.ApplicationValue;
import com.cfl.myproject.controller.RegistController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常
 * 1.捕获返回json格式
 * 2.捕获返回页面
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author msi
 * @Date 2019/7/28 21:51
 */
//@RestController
//@ControllerAdvice(basePackages = "com.cfl.myproject.controller")
//@ControllerAdvice(assignableTypes = {RegistController.class})
public class UserGlobalExceptionHandler {

    @Autowired
    private ApplicationValue applicationValue;

    @Autowired
    private JavaMailSenderImpl mailSender;

    // ModelAndView 返回页面
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, Object> errorJSON(Exception e){
        // 将错误记录在日志中。
        Map<String, Object> errorResultMap = new HashMap<>();
        errorResultMap.put("code", 500);
        errorResultMap.put("errorMsg", "全局捕获异常系统错误");
        errorResultMap.put("detailed", e.getMessage());
        e.printStackTrace();

        // 发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(applicationValue.getApplicationName() + ": 错误日志");	//设置邮件标题
        String text = "尊敬的管理员，您好：\n 项目发生bug，请尽快解决\n" + e + "\n" + LocalDateTime.now();
        message.setText(text);	//设置邮件正文
        message.setTo(applicationValue.getReceiveEmail());	//设置收件人
        message.setFrom(applicationValue.getSenderEmail());	//设置发件人
        mailSender.send(message);	//发送邮件
        return errorResultMap;
    }
}
