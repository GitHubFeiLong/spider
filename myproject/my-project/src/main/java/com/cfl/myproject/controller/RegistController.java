package com.cfl.myproject.controller;

import com.cfl.myproject.config.HelloReceive;
import com.cfl.myproject.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@RequestMapping("/user")
@ResponseBody
public class RegistController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private HelloReceive helloReceive;

    @RequestMapping("/send")
    public String send(){
        String context = "hello:" + LocalDate.now();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGIST_QUEUE, context);
        return "helle";
    }

    @RequestMapping("/receive")
    public String receive(){
//        helloReceive.process();
        return "接收成功";
    }

    /*
    * @GetMapping，处理get请求
    @PostMapping，处理post请求
    @PutMapping，处理put请求
    @DeleteMapping，处理delete请求
    * */
    @PutMapping("/registUser")
    public String registUser(){

        return null;
    }

}
