package com.cfl.jd.controller;

import com.cfl.jd.annotation.RepeatSubmitAnnotation;
import com.cfl.jd.config.MemberVariable;
import com.cfl.jd.exception.BaseException;
import com.cfl.jd.service.UserService;
import com.cfl.jd.util.IpAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 用户登录注册控制器
 * @ClassName UserController
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 14:04
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
@ResponseBody
@Slf4j
public class UserController extends MemberVariable {

    /**
     * 用户登录注册业务层
     */
    @Autowired
    private UserService userService;

    /**
     * 注册时发送 验证码
     * @param email
     * @return
     */
    @RequestMapping("/registSendEmail")
    @RepeatSubmitAnnotation
    public Map sendCodeToEmail(String email) throws BaseException {
        Map<String, Object> msgMap = new HashMap();
        userService.sendCaptcha(email);
        String ip = IpAddressUtil.getIpAddress(httpServletRequest);
        msgMap.put("responseCode", 200);
        return msgMap;
    }


    /**
     * 注册账号
     * @param registUsername
     * @param registPin
     * @return
     */
    @RequestMapping("/registUser")
    @RepeatSubmitAnnotation
    public Map registUser(String registUsername, String registPassword, String registPin) throws Exception {
        Map<String, Object> controllerMap = new HashMap();
        controllerMap.put("responseCode", 200);
        log.info(registUsername);
        log.info(registPin);
        boolean boo = userService.saveUser(registUsername, registPassword, registPin);
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
    @RepeatSubmitAnnotation
    public Map login(String loginUsername, String loginPassword) throws Exception {
        // 给当前用户设置一个token，防止重复提交
        Map<String, Object> controllerMap = new HashMap();
        controllerMap.put("responseCode", 200);
        Map serviceMap = userService.userLogin(loginUsername, loginPassword);
        controllerMap.putAll(serviceMap);
        return controllerMap;
    }
}
