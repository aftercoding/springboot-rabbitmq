package com.sean.springbootrabbitmqdemo.consumer;

import com.sean.springbootrabbitmqdemo.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2023-01-09 21:34
 */
@Slf4j
@Component
public class Consumer {
    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMessage(Message message){
        String msg = new String(message.getBody());
        log.info("Received message from queue :confirm.queue : {}", msg);
    }
}
