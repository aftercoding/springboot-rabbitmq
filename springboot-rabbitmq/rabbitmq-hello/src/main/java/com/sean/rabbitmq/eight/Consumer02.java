package com.sean.rabbitmq.eight;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sean.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @description: dead message
 * Consumer 2
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2023-01-02 16:37
 */
public class Consumer02 {

    public static final String  DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("waiting for receive message:");

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("Consumer01 received message: " + new String(message.getBody(), "UTF-8"));
        };
        channel.basicConsume(DEAD_QUEUE,true, deliverCallback, consumerTag ->{});
    }
}
