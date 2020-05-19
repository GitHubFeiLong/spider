package com.cfl.myproject.config;

import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
