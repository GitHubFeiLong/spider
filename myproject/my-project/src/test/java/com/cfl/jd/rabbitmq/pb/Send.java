package com.cfl.jd.rabbitmq.pb;

import com.cfl.jd.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 原是：声明队列 //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 现：声明交换机，EXCHANGE_NAME(交换机名)，fanout(交换机类型)
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //分发

        for (int i = 0; i < 50; i++) {
            // 发送消息
            String msg = "hello pb" + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
            System.out.println("Send msg = " + msg);
            Thread.sleep(100);
        }

        channel.close();
        connection.close();


    }
}
