package com.cfl.jd.rabbitmq.topic;

import com.cfl.jd.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {
    public static final String EXCHANGE_NAME = "test_exchange_topic";
    public static final String QUEUE_NAME = "test_queue_topic_1";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false,false,false,null);

        //绑定：
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");

        channel.basicQos(1);

        //定义消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[1] recv msg: " + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    System.out.println("[1] recv done!");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        // 监听队列
        channel.basicConsume(QUEUE_NAME, false, consumer);


    }
}
