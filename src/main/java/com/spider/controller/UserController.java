package com.spider.controller;

import com.spider.pojo.ZzUser;
import com.spider.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jingjing
 */
@Controller
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
        Map<String, String> returnMap = null;
        try{
            String code = session.getAttribute("code").toString();
            returnMap = new HashMap<>();
            if(!yanzhengma.equalsIgnoreCase(code)){
                returnMap.put("error","true");
                returnMap.put("message","验证码错误");
                return returnMap;
            }
            ZzUser zzUser = userService.getZzUserByLogin(loginUsername);
            if(null == zzUser){
                returnMap.put("error","true");
                returnMap.put("message","用户名不存在");
                return returnMap;
            }else{
                if(!password.equals(zzUser.getPassword())){
                    returnMap.put("error","true");
                    returnMap.put("message","密码错误");
                    return returnMap;
                }
            }
            session.setAttribute("logiName", loginUsername);
            Cookie cookie = new Cookie("logiName", loginUsername);
            //设置Cookie的最大生命周期,否则浏览器关闭后Cookie即失效
            cookie.setMaxAge(60*60);
            cookie.setPath("/");
            //将Cookie加到response中
            HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookie);
            returnMap.put("success","true");
        } catch(Exception e){
            log.error("登录失败：", e);
        }
        return returnMap;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String TextUserName, String password, String yanzhengma, Model model,HttpSession session, HttpServletRequest request, HttpServletResponse response){


        try{
            String code = session.getAttribute("code").toString();
            if(!yanzhengma.equalsIgnoreCase(code)){
                model.addAttribute("msg","验证码错误");
                return "login";
            }
            ZzUser zzUser = userService.getZzUserByLogin(TextUserName);
            if(null == zzUser){
                model.addAttribute("msg","用户名不存在");
                return "login";
            }else{
                if(!password.equals(zzUser.getPassword())){
                    model.addAttribute("msg","密码错误");
                    return "login";
                }
            }
            session.setAttribute("logiName", TextUserName);
            Cookie cookie = new Cookie("logiName", TextUserName);
            //设置Cookie的最大生命周期,否则浏览器关闭后Cookie即失效
            cookie.setMaxAge(60*60);
            cookie.setPath("/");
            //将Cookie加到response中
//            HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookie);
        } catch(Exception e){
            log.error("登录失败：", e);
        }



        return "father";
    }

    @RequestMapping("/hello")
    public String hello(String TextUserName, String password, String yanzhengma){
        return "index";

    }
}
