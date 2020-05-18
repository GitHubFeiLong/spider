package com.cfl.myproject.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "hello")
@Component
public class HelloReceive {

    @RabbitHandler
    public void process(String msg){
        System.out.println("msg = " + msg);
    }
}
