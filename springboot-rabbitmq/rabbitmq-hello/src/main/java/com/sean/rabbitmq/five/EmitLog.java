package com.sean.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.sean.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @description: 发消息给交换机
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-12-30 18:42
 */
public class EmitLog {
    public static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, "",null, message.getBytes("UTF-8"));
            System.out.println("producer has send message: " + message);
        }
    }
}
