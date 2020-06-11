package com.cfl.jd.queue;

import com.cfl.jd.config.ApplicationValue;
import com.cfl.jd.constant.QueueConsts;
import com.cfl.jd.entity.dto.EmailDTO;
import com.cfl.jd.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 * 监听发送邮件的队列
 * @ClassName SendEmailReceiveQueue
 * @Description TODO
 * @Author msi
 * @Date 2020/6/11 12:51
 * @Version 1.0
 */
@Slf4j
@Component
public class SendEmailReceiveQueue {

    /**
     * 邮箱工具类
     */
    @Autowired
    private EmailUtil emailUtil;

    /**
     * 自定义配置的类
     */
    @Autowired
    private ApplicationValue applicationValue;

    /**
     * 监听rabbitMQ的队列：QueueConsts.SEND_EMAIL_QUEUE；发送邮件
     * @param emailDTO 发送邮件的消息数据
     */
    @RabbitListener(queues = QueueConsts.SEND_EMAIL_QUEUE)
    @RabbitHandler
    public void process(EmailDTO emailDTO){
        log.info("rabbitMQ队列{}接收消息成功，开始发送邮件", QueueConsts.SEND_EMAIL_QUEUE);
        emailUtil.sendSimpleEmail(applicationValue.getSenderEmail(), emailDTO.getReceiver(), emailDTO.getTopic(), emailDTO.getContext(),
                emailDTO.getEnd());
        log.info("rabbitMQ队列{}消费消息成功，邮件发送完成", QueueConsts.SEND_EMAIL_QUEUE);
    }
}
