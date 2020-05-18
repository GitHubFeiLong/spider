package com.cfl.myproject.rabbitmq.work;

import com.cfl.myproject.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static final String QUEUE_NAME = "test_work_queue";
    /**
     *                  |--> C2
     * P ---> Queue ----|
     *                  |--> C1
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取channel
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 发送消息
        for (int i = 0; i < 50; i++) {
            String msg= "send hello " + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("【WQ】 send msg = " + msg);
            Thread.sleep(i*20);
        }
        // 关闭资源
        channel.close();
        connection.close();

    }
}
