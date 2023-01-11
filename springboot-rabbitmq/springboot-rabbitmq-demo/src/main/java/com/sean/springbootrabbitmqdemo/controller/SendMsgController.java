package com.sean.springbootrabbitmqdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2023-01-04 18:43
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("sendMsg/{message}")
    public void  sendMsg(@PathVariable String message){
        log.info("current time: {}, send a message to two ttl queue:{}", new Date().toString(),message);
        rabbitTemplate.convertAndSend("X", "XA", "message from ttl 10s queue: " + message);
        rabbitTemplate.convertAndSend("X", "XB", "message from ttl 40s queue: " + message);
    }
    //开始发消息 和 ttl
    @GetMapping("sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime){

        rabbitTemplate.convertAndSend("X", "XC", message, msg ->{
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
        log.info("current time: {}, send a message with ttl {} ms,  to queue QC:{}", new Date().toString(),ttlTime,message);
    }

    //开始发布确认 测试确认


}
