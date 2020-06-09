package com.cfl.jd.controller;

import cn.hutool.core.lang.UUID;
import com.cfl.jd.annotation.RepeatSubmit;
import com.cfl.jd.controller.parent.MemberVariable;
import com.cfl.jd.service.RegistService;
import com.cfl.jd.util.IpAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录注册控制器
 */
@Controller
@RequestMapping("/user")
@ResponseBody
@Slf4j
public class RegistController extends MemberVariable {

    /**
     * 用户登录注册业务层
     */
    @Autowired
    private RegistService registService;

    /**
     * 注册时发送 验证码
     * @param email
     * @return
     */
    @RequestMapping("/registSendEmail")
    public Map sendCodeToEmail(String email){
        Map<String, Object> msgMap = new HashMap();
        log.info("进入：" + email);
        registService.sendCaptcha(email);
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
    public Map registUser(String registUsername, String registPassword, String registPin) throws Exception {
        Map<String, Object> controllerMap = new HashMap();
        controllerMap.put("responseCode", 200);
        log.info(registUsername);
        log.info(registPin);
        boolean boo = registService.saveUser(registUsername, registPassword, registPin);
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
    @RepeatSubmit
    public Map login(String loginUsername, String loginPassword) throws Exception {
        // 给当前用户设置一个token，防止重复提交
        httpSession.setAttribute("uniqueToken", UUID.randomUUID());
        Map<String, Object> controllerMap = new HashMap();
        controllerMap.put("responseCode", 200);
        Map serviceMap = registService.userLogin(loginUsername, loginPassword);
        controllerMap.putAll(serviceMap);
        return controllerMap;
    }
}
