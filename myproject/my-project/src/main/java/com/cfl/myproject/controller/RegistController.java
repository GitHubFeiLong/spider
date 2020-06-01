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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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


    /**
     * 注册账号
     * @param registUsername
     * @param registPin
     * @return
     */
    @RequestMapping("/registUser")
    public Map registUser(String registUsername, String registPassword, String registPin) throws Exception {
        Map<String, Object> controllerMap = new HashMap();
        controllerMap.put("code", 200);
        log.info(registUsername);
        log.info(registPin);
        boolean boo = registService.registUser(registUsername, registPassword, registPin);
        controllerMap.put("captchaMatch", boo);
        return controllerMap;
    }


    /**
     * 登录
     * 有三种状态：第一种直接登录成功
     *          第二种账号不存在
     *          第三种账号密码不匹配
     * @param loginUsername
     * @param loginPassword
     * @return
     */
    @RequestMapping("/login")
    public Map login(String loginUsername, String loginPassword) throws Exception {
        Map<String, Object> controllerMap = new HashMap();
        controllerMap.put("code", 200);
        Map serviceMap = registService.userLogin(loginUsername, loginPassword);
        controllerMap.putAll(serviceMap);
       return controllerMap;
    }
}
