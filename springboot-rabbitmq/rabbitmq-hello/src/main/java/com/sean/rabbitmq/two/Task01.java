package com.sean.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.sean.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-12-26 18:30
 */
public class Task01 {
    public  static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        /**
         * 生成队列
         * 1. 队列名称
         * 2. 队列里的消息是否持久化
         * 3. 改对了是否只供一个消费者消费
         * 4. 是否自动删除，
         * 5. 其他参数
         * */
        channel.queueDeclare(QUEUE_NAME,false, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
           String next = scanner.next();
           channel.basicPublish("", QUEUE_NAME, null, next.getBytes());
            System.out.println(" message: " + next + " has been sent");
        }
    }
}
