package com.cfl.myproject.service;

public interface RegistService {

    /**
     * 发送验证码
     * receiver: 接收者
     */
    void sendCaptcha(String receiver);

    boolean registUser(String email, String captcha);
}
