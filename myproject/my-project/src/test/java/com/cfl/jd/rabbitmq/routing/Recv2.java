package com.cfl.jd.rabbitmq.routing;

import com.cfl.jd.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    public static final String EXCHANGE_NAME = "test_exchange_direct";
    public static final String QUEUE_NAME = "test_queue_direct_2";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false,null);

        // 绑定队列到交换机，并绑定 routingKey
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "waring");

        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("Recv [2] msg = " + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println("Recv [2] done!");
                }
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
