package com.cfl.jd.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 * 类描述：
 * 用户模块的业务层接口
 * @ClassName UserService
 * @Description TODO
 * @Author msi
 * @Date 2020/6/11 19:30
 * @Version 1.0
 */
public interface UserService {

    /**
     * 发送验证码
     * @param receiver: 接收者
     */
    void sendCaptcha(String receiver);

    /**
     * 注册账号
     * @param email 用户邮箱
     * @param password 用户密码
     * @param captcha 用户输入验证码
     * @return true:注册成功，false注册失败
     * @throws NoSuchAlgorithmException
     */
    boolean saveUser(String email, String password, String captcha) throws NoSuchAlgorithmException, InvalidKeySpecException;


    /**
     * 用户登录
     * @param loginUsername 登录用户名/电话/邮箱
     * @param loginPassword 密码
     * @return map对象
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    Map userLogin(String loginUsername, String loginPassword) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
