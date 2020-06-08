package com.cfl.myproject.controller.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName MemberVariable
 * @Author msi
 * @Date 2020/6/8 13:10
 * @Version 1.0
 */
@Component
public class MemberVariable {
    /**
     * 请求对象
     */
    @Autowired
    public HttpServletRequest httpServletRequest;

    /**
     * session
     */
    @Autowired
    public HttpSession httpSession;
}
