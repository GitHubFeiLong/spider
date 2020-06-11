package com.cfl.jd.config;

import com.cfl.jd.constant.QueueConsts;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述：
 * 配置RabbitMQ的队列
 * @ClassName RabbitMQConfig
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:29
 * @Version 1.0
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 创建一个发送邮件的队列
     * @return Queue
     */
    @Bean
    public Queue sendEmailQueue(){
        return new Queue(QueueConsts.SEND_EMAIL_QUEUE);
    }


}
