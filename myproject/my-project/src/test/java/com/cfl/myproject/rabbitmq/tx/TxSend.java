package com.cfl.myproject.rabbitmq.tx;

import com.cfl.myproject.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {
    public static final String QUEUE_NAME = "test_queue_tx";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello tx message";

        try{
            // 开启事务
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println(1/0);
            //提交事务
            channel.txCommit();
        } catch(Exception e){
            // 回滚事务
            channel.txRollback();
            System.out.println("send message txRollback");
        } finally{
            channel.close();
            connection.close();
        }
    }
}
