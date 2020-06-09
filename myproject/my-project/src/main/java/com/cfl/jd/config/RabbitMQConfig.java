package com.cfl.jd.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_REGIST_QUEUE = "userRegistQueue";
    public static final String GOODS_SPIKE_QUEUE = "goodsSpikeQueue";

    @Bean
    public Queue userRegistQueue(){
        return new Queue(RabbitMQConfig.USER_REGIST_QUEUE);
    }
    @Bean
    public Queue goodsSpikeQueue(){
        return new Queue(RabbitMQConfig.GOODS_SPIKE_QUEUE);
    }

}
