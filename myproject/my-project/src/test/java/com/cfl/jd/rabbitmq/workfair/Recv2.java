package com.cfl.jd.rabbitmq.workfair;

import com.cfl.jd.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    public static final String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取channel
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false,null);

        channel.basicQos(1);
        // 定义一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            // 消息到达，触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("Recv [2] msg = " + msg);
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    System.out.println("Recv [2] done!");
                    // 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        // 监听队列
        boolean autoAck = false; //false 手动回执
        channel.basicConsume(QUEUE_NAME, autoAck, defaultConsumer);

    }
}
