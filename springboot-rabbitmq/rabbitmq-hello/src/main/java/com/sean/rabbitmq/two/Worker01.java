package com.sean.rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sean.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-12-26 17:20
 */
public class Worker01 {
    public static  final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println(new String("Received : " + new String(message.getBody())));
        };
        CancelCallback cancelCallback = (consumerTag) ->{
            System.out.println(consumerTag + " message has been canceled by consumer");
        };
        /**
         * 接收消息
         * 1. 消费哪个队列
         * 2. 消费成功之后是否自动应答 true， 代表的自动应答 false 代表手动应答
         * 3. 消费者未成功消费的回调
         * 4. 消费者取消消费的回调
         */
        System.out.println("C2 is waitting message");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
