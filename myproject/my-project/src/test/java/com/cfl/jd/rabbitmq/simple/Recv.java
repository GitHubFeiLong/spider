package com.cfl.jd.rabbitmq.simple;

import com.cfl.jd.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者，获取消息
 */
public class Recv {
    private static final String QUERE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 队列声明
        channel.queueDeclare(QUERE_NAME, false, false,false, null);

        // 定义队列的消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

            // 获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                super.handleDelivery(consumerTag, envelope, properties, body);
                String msgString = new String(body,"utf-8");
                System.out.println("new api recv: " + msgString);
            }
        };

        // 监听队列
        channel.basicConsume(QUERE_NAME, true, defaultConsumer);
    }
}
