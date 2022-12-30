package com.sean.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sean.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-12-30 16:54
 */
public class Receive02 {
    public static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //声明一个临时队列
        /**
         * 生成一个临时对了，队列的名称是随机的
         * 当消费者断开与队列的连接的时候， 队列就自动删除。
         *
         */
        String queueName = channel.queueDeclare().getQueue();
        /**
         * 绑定交换机
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("ReceiveLogs02, waiting for message, and print message!");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("ReceiveLogs02, print console receive message: " + new String(message.getBody(), "UTF-8"));
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag ->{});
    }
}
