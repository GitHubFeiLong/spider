package com.cfl.jd.queue;

import com.cfl.jd.config.ApplicationValue;
import com.cfl.jd.config.RabbitMQConfig;
import com.cfl.jd.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听队列 RabbitMQConfig.USER_REGIST_QUEUE
 * 使用 @RabbitHandler注解的方法 消费队列
 */
@RabbitListener(queues = RabbitMQConfig.USER_REGIST_QUEUE)
@Component
@Slf4j
public class UserRegistQueueReceive {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private ApplicationValue applicationValue;

    /**
     * 发送邮件
     * @param receiver 收件人邮箱
     */
    @RabbitHandler
    public void process(String receiver){
        log.info("开始发送注册成功的邮件");
        String subject = "注册成功";
        String context = "恭喜您，注册账号成功，去登陆吧\n";
        emailUtil.sendSimpleEmail(applicationValue.getSenderEmail(), receiver,subject, context);
        log.info("送注册邮件成功");
    }
}
