package com.sean.springbootrabbitmqdemo.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2023-01-04 21:22
 */
@Component
@Slf4j
public class DeadLetterQueueConsumer {
    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("current time: {}, receive message from dead letter queue: {}", new Date().toString(), msg);
    }
}
