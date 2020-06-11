package com.cfl.jd.service.impl;

import com.cfl.jd.constant.CacheConsts;
import com.cfl.jd.constant.QueueConsts;
import com.cfl.jd.config.MemberVariable;
import com.cfl.jd.dao.UserDAO;
import com.cfl.jd.entity.UserDO;
import com.cfl.jd.entity.dto.EmailDTO;
import com.cfl.jd.enumerate.UserExceptionEnum;
import com.cfl.jd.enumerate.UserLoginEnum;
import com.cfl.jd.exception.UserException;
import com.cfl.jd.service.UserService;
import com.cfl.jd.util.GetNowUtil;
import com.cfl.jd.util.IpAddressUtil;
import com.cfl.jd.util.PasswordEncryptionUtil;
import com.cfl.jd.util.VerCodeGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 用户业务类
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author msi
 * @Date 2020/6/11 17:20
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends MemberVariable implements UserService {

    /**
     * 用户注册登录数据层
     */
    @Autowired
    private UserDAO userDAO;


    /**
     * 发送验证码
     * @param receiver: 接收者
     */
    @Override
    public void sendCaptcha(String receiver) {
        // 1. 先查询邮箱是否已经存在
        UserDO user = userDAO.selectUserByEmail(receiver);
        // 2.判断，不存在进入if语句内，发送验证码。
        if (ObjectUtils.isEmpty(user)) {
            int expiredTime = applicationValue.getCaptchaExpiredTime();
            String verCode = VerCodeGenerateUtil.generateVerCode();
            // 放置验证码到redis中 key : 使用了sessionId保证一个用户对应一条
            super.redisUtil.set(CacheConsts.REGIST_CAPTCHA + httpSession.getId(), verCode, expiredTime);

            // 给rabbitmq的QueueConsts.SEND_EMAIL_QUEUE发送消息
            String emailTopic = "欢迎注册账号";
            String emailContext = "尊敬的用户,您好:    \n\n" +
                    "本次请求的邮件验证码为:" + verCode + "（请勿泄露此验证码）,本验证码5分钟内有效，请及时输入。\n" +
                    "如非本人操作，请忽略该邮件(这是一封自动发送的邮件，请不要直接回复）。";
            String emailEnd = applicationValue.getApplicationName() + "\n" + GetNowUtil.getDateTime();
            EmailDTO emailDTO = new EmailDTO(receiver, emailTopic, emailContext, emailEnd);
            super.rabbitTemplate.convertAndSend(QueueConsts.SEND_EMAIL_QUEUE, emailDTO);

        } else {
            // 3.邮箱已被注册
            throw new UserException(UserExceptionEnum.MAILBOX_IS_EXIST);
        }
    }

    /**
     * 验证 验证码是否匹配
     * @param email
     * @param password
     * @param captcha
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Override
    public boolean saveUser(String email, String password, String captcha) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // redis 取
        String verCode = (String) redisUtil.get(CacheConsts.REGIST_CAPTCHA + httpSession.getId());
        // 验证码 与 用户输入的验证码 比较
        if (verCode.equals(captcha)) {

            // 给rabbitmq的QueueConsts.SEND_EMAIL_QUEUE发送消息
            String emailTopic = "注册成功";
            String emailContext = "恭喜您，注册账号成功，去登陆吧";
            String emailEnd = applicationValue.getApplicationName() + "\n" + GetNowUtil.getDateTime();
            EmailDTO emailDTO = new EmailDTO(email, emailTopic, emailContext, emailEnd);
            super.rabbitTemplate.convertAndSend(QueueConsts.SEND_EMAIL_QUEUE, emailDTO);

            String ip = IpAddressUtil.getIpAddress(httpServletRequest);
            // 获取随机盐 和 加密后的密码
            String salt = PasswordEncryptionUtil.generateSalt();
            String encryptionPassword = PasswordEncryptionUtil.getEncryptedPassword(password, salt);

            // 设置保存user到数据库
            UserDO user = new UserDO();
            user.setEmail(email);
            user.setSalt(salt);
            user.setPassword(encryptionPassword);
            user.setIp(ip);
            user.setStatus(0);
            user.setCreateTime(GetNowUtil.getDateTime());
            userDAO.insertUser(user);
            return true;
        } else {
            // 验证码不匹配
            throw new UserException(UserExceptionEnum.CAPTCHA_MISMATCH);
        }

    }

    /**
     * 用户登录
     * @param loginUsername 登录用户名/电话/邮箱
     * @param loginPassword 密码
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public Map userLogin(String loginUsername, String loginPassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Map<String, Object> serviceMap = new HashMap<>();

        // 1. 先查询用户信息
        UserDO user = userDAO.selectUserByNameOrEmailOrPhone(loginUsername);
        String msg = UserLoginEnum.USER_NOT_EXIST.getMessage();    // 具体信息，默认登录用户不存在
        Integer responseCode = UserLoginEnum.USER_NOT_EXIST.getCode();
        boolean passwordIsSame = false; // false 表示不能登录
        // 用户存在
        if (!ObjectUtils.isEmpty(user)) {
            // 2.1.将密码 和用户的随机盐 加密
            String salt = user.getSalt();
            String password = user.getPassword();
            // 判断密码是否相同
            passwordIsSame = PasswordEncryptionUtil.authenticate(loginPassword, password, salt);
            if(passwordIsSame){
                msg = UserLoginEnum.USER_PASSWORD_MATCH.getMessage();
                responseCode = UserLoginEnum.USER_PASSWORD_MATCH.getCode();

                // 给rabbitmq的QueueConsts.SEND_EMAIL_QUEUE发送消息
                String emailTopic = "欢迎登录";
                String emailContext = "尊敬的用户,您好:\n\n 欢迎登录XXX(这是一封自动发送的邮件，请不要直接回复）\n";
                String emailEnd = applicationValue.getApplicationName() + "\n" + GetNowUtil.getDateTime();
                EmailDTO emailDTO = new EmailDTO(user.getEmail(), emailTopic, emailContext, emailEnd);
                super.rabbitTemplate.convertAndSend(QueueConsts.SEND_EMAIL_QUEUE, emailDTO);

            } else {
                // 密码不匹配
                throw new UserException(UserExceptionEnum.USER_WRONG_PASSWORD);
            }

        } else {
            // 用户不存在
            throw new UserException(UserExceptionEnum.USERNAME_NOT_EXIST);
        }

        // 状态码
        serviceMap.put("responseCode", responseCode);
        // 错误信息
        serviceMap.put("message", msg);

        return serviceMap;
    }


}
