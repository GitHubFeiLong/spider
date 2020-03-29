package com.spider.controller;

import com.spider.pojo.ZzUser;
import com.spider.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jingjing
 */
@RestController
@Slf4j
@RequestMapping("/login")
public class UserController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @RequestMapping("/checkLogin")
    @ResponseBody
    public Map<String, String> checkLogin(String loginUsername,String password,String yanzhengma){
        Map<String, String> map = null;
        try{
            String code = session.getAttribute("code").toString();
            if(!yanzhengma.equalsIgnoreCase(code)){
                System.out.println("验证码不对");
                return map;
            }
            map = new HashMap<>();
            map.put("hello","hello");
            ZzUser zzUserById = userService.getZzUserById(1);

        } catch(Exception e){
            log.error("登录失败：", e);
        }
        return map;

    }

    @RequestMapping("/hello")
    @ResponseBody
    public Map<String, String> hello(){
        Map<String, String> map = null;
        System.out.println("123");
        return map;

    }
}
