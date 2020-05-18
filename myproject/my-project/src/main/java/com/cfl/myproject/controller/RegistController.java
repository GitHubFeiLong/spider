package com.cfl.myproject.controller;

import com.cfl.myproject.config.HelloReceive;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@ResponseBody
public class RegistController {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    @RequestMapping("/send")
    public String send(){
        String context = "hello:" + LocalDate.now();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
        return "helle";
    }

    @RequestMapping("/receive")
    public String receive(){
//        helloReceive.process();
        return "接收成功";
    }

}
