package com.sean.springbootrabbitmqdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2023-01-10 21:04
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 注入：将 本实现类（this）注入到rabbitTemplate 中
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
    * 交换机确认回调方法
     * 1. 发消息 交换机收到了 回调
     * 1.1 correlationData 保存回调消息的ID及相关信息
     * 1.2 交换机收到消息 ack = true
     * 1.3 cause null
     * 2. 发消息 交换机接受失败了 回调
     * 2.1 correlationData 保存回调消息的ID及相关信息
     * 2.2 交换机收到消息 ack = false
     * 2.3 cause 失败的原因
    * */

    //回调接口
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if(b){
            log.info("Exchange has received massage :{} ",id);
        }else{
//            log.info("Exchange has not receive message id {}, because: ", id, s);
        }
    }
}
