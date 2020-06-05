package com.cfl.myproject.service.impl;

import com.cfl.myproject.config.ApplicationValue;
import com.cfl.myproject.config.RabbitMQConfig;
import com.cfl.myproject.dao.UserRegistDao;
import com.cfl.myproject.entity.User;
import com.cfl.myproject.service.RegistService;
import com.cfl.myproject.util.*;
import javafx.beans.binding.ObjectExpression;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import com.cfl.myproject.entity.User;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegistServiceImpl implements RegistService {

    /**
     * 注册账号，保存验证码的到redis 的key前缀
     */
    public static final String REGIST_CAPTCHA = "jd:user:regist:captcha:";

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private ApplicationValue applicationValue;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserRegistDao userRegistDao;

    @Autowired
    private EmailUtil emailUtil;
    /**
     * 发送验证码
     */
    @Override
    public void sendCaptcha(String receiver) {
        int expiredTime = 3000;
        String verCode = VerCodeGenerateUtil.generateVerCode();

        // 放置验证码到redis中 key : 使用了sessionId保证一个用户对应一条
        this.redisUtil.set(REGIST_CAPTCHA + httpSession.getId(), verCode, expiredTime);

        String emailSubject = "欢迎注册账号";
        String emailContext = "尊敬的用户,您好:\n"
                + "\n本次请求的邮件验证码为:" + verCode + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）";
        // 发送邮件
        emailUtil.sendSimpleEmail(applicationValue.getSenderEmail(), receiver, emailSubject, emailContext);
    }

    /**
     * 验证 验证码是否匹配
     * @param email
     * @param password
     * @param captcha
     * @return
     * @throws NoSuchAlgorithmException
     */
    @Override
    public boolean registUser(String email, String password, String captcha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // redis 取
        String verCode = (String) redisUtil.get(REGIST_CAPTCHA + httpSession.getId());
        // 验证码 与 用户输入的验证码 比较
        if(verCode.equals(captcha)){
            // 匹配，注册邮箱
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGIST_QUEUE, email);
            String ip = IpAddressUtil.getIpAddress(httpServletRequest);
            // 获取随机盐 和 加密后的密码
            String salt = PasswordEncryption.generateSalt();
            String encryptionPassword = PasswordEncryption.getEncryptedPassword(password, salt);

            User user = new User();
            user.setEmail(email);
            user.setSalt(salt);
            user.setPassword(encryptionPassword);
            user.setIp(ip);
            user.setStatus(0);
            user.setCreateTime(GetNowUtil.getDateTime());
            userRegistDao.insertUser(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map userLogin(String loginUsername, String loginPassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Map<String, Object> serviceMap = new HashMap<>();
        // 1. 先查询用户信息
        User user = userRegistDao.selectUserByNameOrEmailOrPhone(loginUsername);
        String msg = "用户不存在";    // 具体信息，默认登录用户不存在
        String code = "";
        boolean passwordIsSame = false; // false 表示不能登录
        // 2.判断用户是否存在
        if(!ObjectUtils.isEmpty(user)){
            // 2.1.将密码 和用户的随机盐 加密
            String salt = user.getSalt();
            String password = user.getPassword();
            // 判断密码是否相同
            passwordIsSame = PasswordEncryption.authenticate(loginPassword, password, salt);
            if(passwordIsSame){
                msg = "登录验证通过";
                code = applicationValue.getUserPasswordMatch();
                String emailSubject = "欢迎注册账号";
                String emailContext = "尊敬的用户,您好:\n"
                        + "\n欢迎登录XXX(这是一封自动发送的邮件，请不要直接回复）\n" + LocalDateTime.now().toLocalDate() + "-" + LocalDateTime.now().toLocalTime();
                // 发送邮件
                emailUtil.sendSimpleEmail(applicationValue.getSenderEmail(), user.getEmail(), emailSubject, emailContext);
            } else {
                msg = "密码错误";
                code = applicationValue.getUserWrongPassword();
            }
        } else {
            code = applicationValue.getUserNotExist();
        }


        serviceMap.put("code", code);
        serviceMap.put("isSame", passwordIsSame);
        serviceMap.put("message", msg);


        return serviceMap;
    }


}
