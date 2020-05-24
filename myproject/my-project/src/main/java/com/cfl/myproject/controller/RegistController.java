package com.cfl.myproject.controller;

import com.cfl.myproject.config.ApplicationValue;
import com.cfl.myproject.service.RegistService;
import com.cfl.myproject.util.IpAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@ResponseBody
@Slf4j
public class RegistController {

    @Autowired
    private RegistService registService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private HttpServletRequest httpServletRequest;


    /*
    * @GetMapping，处理get请求
    @PostMapping，处理post请求
    @PutMapping，处理put请求
    @DeleteMapping，处理delete请求
    * */
    /*@PutMapping("/registUser")
    public String registUser(){

        return null;
    }*/

    @RequestMapping("/test")
    public String test(){
        log.info("进入测试log4j2");
        log.error("进入测试log4j2");
        return "hello log4j";
    }

    /**
     * 发送 验证码
     * @param email
     * @return
     */
    @RequestMapping("/registSendEmail")
    public Map sendCodeToEmail(String email){
        Map<String, Object> msgMap = new HashMap();
        log.info("进入：" + email);
        registService.sendCaptcha(email);
        String ip = IpAddressUtil.getIpAddress(httpServletRequest);
        log.info(ip);
        msgMap.put("code", 200);
        return msgMap;
    }


    @RequestMapping("/registUser")
    public Map registUser(String registUsername, String registPin){
        Map<String, Object> msgMap = new HashMap();
        msgMap.put("code", 200);
//        msgMap.put("");
        log.info(registUsername);
        log.info(registPin);
        boolean boo = registService.registUser(registUsername, registPin);
        msgMap.put("captchaMatch", boo);
        return msgMap;
    }
}
