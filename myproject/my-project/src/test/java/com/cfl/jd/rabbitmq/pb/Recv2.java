package com.cfl.jd.rabbitmq.pb;

import com.cfl.jd.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    public static final String QUEUE_NAME = "test_queue_fanout_sms";
    public static final String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取channel
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false,null);

        // 将队列绑定到交换机 转发器
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        // 保证一次只分发一个
        channel.basicQos(1);
        // 定义一个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            // 消息到达，触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("Recv [2] msg = " + msg);
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    System.out.println("Recv [2] done!");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        // 监听队列
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
