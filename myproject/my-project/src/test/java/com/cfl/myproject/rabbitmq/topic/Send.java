package com.cfl.myproject.rabbitmq.topic;

import com.cfl.myproject.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String message = "商品...";

        channel.basicPublish(EXCHANGE_NAME, "goods.delete", null, message.getBytes());

        System.out.println("send message = " + message);

        channel.close();
        connection.close();

    }

}
