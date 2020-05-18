package com.cfl.myproject.rabbitmq.routing;

import com.cfl.myproject.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String msg = "hello direct !";
        // routing key
        String routingKey = "error";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());

        System.out.println("send :" + msg);

        channel.close();
        connection.close();

    }
}
