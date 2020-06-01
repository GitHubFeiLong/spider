package com.cfl.myproject.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface RegistService {

    /**
     * 发送验证码
     * receiver: 接收者
     */
    void sendCaptcha(String receiver);

    /**
     * 注册账号
     * @param email
     * @param password
     * @param captcha
     * @return
     * @throws NoSuchAlgorithmException
     */
    boolean registUser(String email, String password, String captcha) throws NoSuchAlgorithmException, InvalidKeySpecException;


    Map userLogin(String loginUsername, String loginPassword) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
