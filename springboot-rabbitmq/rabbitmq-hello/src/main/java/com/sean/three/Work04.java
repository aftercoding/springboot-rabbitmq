package com.sean.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sean.rabbitmq.utils.RabbitMqUtils;
import com.sean.rabbitmq.utils.SleepUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-12-27 12:37
 */
public class Work04 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2 is waiting for message and need long time");

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            SleepUtils.sleep(30);
            System.out.println("Received message: " + new String(message.getBody(), "UTF-8"));
            /**
             * 手动应答
             * 1. 消息的标记 tag
             * 2. 是否批量应答 false : 不批量应答
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        int perfectchCount = 1;
        channel.basicQos(perfectchCount);
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, (consumerTag ->{
            System.out.println(consumerTag + " consumer consume interface call back logic");
        }));
    }
}
