package com.cfl.myproject.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = RabbitMQConfig.USER_REGIST_QUEUE)
@Component
public class HelloReceive {

    @RabbitHandler
    public void process(String msg){
        System.out.println("msg = " + msg);
    }
}
