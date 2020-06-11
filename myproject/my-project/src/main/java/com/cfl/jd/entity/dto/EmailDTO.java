package com.cfl.jd.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：
 * 给RabbitMQ的邮件队列,发送的消息实体类
 * @ClassName EmailDTO
 * @Description TODO
 * @Author msi
 * @Date 2020/6/11 15:51
 * @Version 1.0
 */
@Data
public class EmailDTO implements Serializable {

    private static final long serialVersionUID = -1571236267323289805L;

    private String receiver; // 收件人
    private String topic; // 主题
    private String context; // 主体内容
    private String end; // 结尾

    public EmailDTO(String receiver, String topic, String context, String end) {
        this.receiver = receiver;
        this.topic = topic;
        this.context = context;
        this.end = end;
    }
}

