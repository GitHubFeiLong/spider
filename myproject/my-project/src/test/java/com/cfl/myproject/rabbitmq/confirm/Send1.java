package com.cfl.myproject.rabbitmq.confirm;

import com.cfl.myproject.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 普通模式
 */
public class Send1 {
    public static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 生产者调用confirmSelect 将chancel设置为confirm模式。注意（事务机制改为这个会出异常）
        channel.confirmSelect();

        String msg = "hello confirm message";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("send message txRollback");

        // 判断是否发送成功
        if(!channel.waitForConfirms()){
            System.out.println("message send failed");
        } else {
            System.out.println("message send ok");
        }

        channel.close();
        connection.close();
    }
}