package com.cfl.myproject.rabbitmq.simple;

import com.cfl.myproject.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUERE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取一个连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中获取一个通道
        Channel channel = connection.createChannel();

        // 声明一个队列
        channel.queueDeclare(QUERE_NAME, false, false, false, null);

        String message = "hello simple !";

        channel.basicPublish("", QUERE_NAME, null, message.getBytes());

        System.out.println("send message");

        channel.close();
        connection.close();

    }
}
