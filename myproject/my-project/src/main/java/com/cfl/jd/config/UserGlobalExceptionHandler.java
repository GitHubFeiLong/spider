package com.cfl.jd.config;


import com.cfl.jd.config.ApplicationValue;
import com.cfl.jd.constant.QueueConsts;
import com.cfl.jd.controller.RegistController;
import com.cfl.jd.controller.parent.MemberVariable;
import com.cfl.jd.entity.dto.EmailDTO;
import com.cfl.jd.exception.BaseException;
import com.cfl.jd.util.GetNowUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常
 * 1.捕获返回json格式
 * 2.捕获返回页面
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author msi
 * @Date 2019/7/28 21:51
 */
@Slf4j
//@ControllerAdvice(basePackages = "com.cfl.myproject.controller")
@ControllerAdvice(assignableTypes = {RegistController.class})
public class UserGlobalExceptionHandler extends MemberVariable {

    @Autowired
    private ApplicationValue applicationValue;

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 捕获全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> errorJSON(BaseException e){
        // 将错误记录在日志中。
        Map<String, Object> errorResultMap = new HashMap<>();
        errorResultMap.put("responseCode", e.getCode());
        errorResultMap.put("detailed", e.getMessage() + "\n" + GetNowUtil.getDateTime());

        // 打印错误日志
        log.error("错误代码({}),错误信息({})", e.getCode(), e.getMessage());

        // 给发送邮件的队列 QueueConsts.SEND_EMAIL_QUEUE 生成消息
        String emailTopic = "错误日志";
        String emailContext = "尊敬的管理员，您好：\n 项目发生bug，请尽快解决\n" + "错误代码("+e.getCode()+"),错误信息("+e.getMessage()+")\n(这是一封自动发送的邮件，请不要直接回复）";
        String emailEnd = applicationValue.getApplicationName() + "\n" + GetNowUtil.getDateTime();
        EmailDTO emailDTO = new EmailDTO(applicationValue.getReceiveEmail(), emailTopic, emailContext, emailEnd);
        super.rabbitTemplate.convertAndSend(QueueConsts.SEND_EMAIL_QUEUE, emailDTO);

        return errorResultMap;
    }

}
