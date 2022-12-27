package com.sean.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-12-24 18:56
 */
public class Producer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        //连接队列
        factory.setHost("8.134.161.68");
        factory.setUsername("admin");
        factory.setPassword("admin");
//        factory.setPort(5672);
//        factory.setVirtualHost("/");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
    /**
    * 生成队列
    * 1. 队列名称
    * 2. 队列里的消息是否持久化
    * 3. 改对了是否只供一个消费者消费
    * 4. 是否自动删除，
    * 5. 其他参数
    * */
        channel.queueDeclare(QUEUE_NAME,false, false,false, null);
        String message = "hello world";
        /**
         * 发送一个消费
         * 1. 发送到哪个交换机
         * 2. 路由的key是哪个，本次队列的名称
         * 3. 其他参数消息
         * 4. 发送的消息消息体
         */
        channel.basicPublish("",QUEUE_NAME, null, message.getBytes());
        System.out.println("message has been sent");

    }
}
