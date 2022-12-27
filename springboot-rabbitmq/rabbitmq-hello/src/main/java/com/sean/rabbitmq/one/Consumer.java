package com.sean.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-12-24 22:00
 */
public class Consumer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.134.161.68");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明 接收消息
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println(new String(message.getBody()));
        };

        //取消消息时的回调。
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("consume message interrupt ");
        };
        /**
         * 接收消息
         * 1. 消费哪个队列
         * 2. 消费成功之后是否自动应答 true， 代表的自动应答 false 代表手动应答
         * 3. 消费者未成功消费的回调
         * 4. 消费者取消消费的回调
         */

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);

    }
}
