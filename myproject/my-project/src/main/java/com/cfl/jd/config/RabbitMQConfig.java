package com.cfl.jd.config;

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
     * 用户注册时使用的队列
     */
    public static final String USER_REGIST_QUEUE = "userRegistQueue";

    /**
     *
     */
    public static final String GOODS_SPIKE_QUEUE = "goodsSpikeQueue";

    /**
     * 创建用户注册时发送验证码的队列
     * @return Queue
     */
    @Bean
    public Queue userRegistQueue(){
        return new Queue(RabbitMQConfig.USER_REGIST_QUEUE);
    }

    @Bean
    public Queue goodsSpikeQueue(){
        return new Queue(RabbitMQConfig.GOODS_SPIKE_QUEUE);
    }

}
