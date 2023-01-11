package com.sean.springbootrabbitmqdemo.controller;

import com.rabbitmq.client.AMQP;
import com.sean.springbootrabbitmqdemo.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 开始发消息
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2023-01-09 20:55
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/sendMessage/{message}")
    public void sendmessage(@PathVariable String message){
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME ,
                ConfirmConfig.CONFIRM_ROUTING_KEY, message,correlationData);
        log.info("Send message: {}" , message);
    }
}
