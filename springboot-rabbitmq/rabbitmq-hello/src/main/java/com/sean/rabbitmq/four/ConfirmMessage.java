package com.sean.rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.sean.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * 1. 单个确认
 * 2. 批量确认
 * 3. 异步批量确认
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-12-29 19:15
 */
public class ConfirmMessage {
    public static final int MESSAGE_COUNT = 1000;
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、单个确认
        //2、批量确认
        //3、异步批量确认
        ConfirmMessage.publishMessageAsync();
    }

    public static void publishMessageAsync() throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        /**
         * 线程安全有序的哈希表， 高并发
         * 1. 将序号与消息进行关联
         * 2. 可以批量删除已经确认的消息
         * 3. 支持高并发
         */

        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        ConfirmCallback ackCallBack = (deliveryTag, multiple) ->{
            if(multiple){
                //删除已经确认的消息 剩下的就是未确认的消息
                //如果是多个消息，则deliveryTag是最后一个消息tag，将这个tag之前的所有key返回成一个 headMap， 删除掉。
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap((deliveryTag));
                confirmed.clear();
            }else{
                outstandingConfirms.remove(deliveryTag);
            }

            System.out.println("confirmed message: " + deliveryTag);
        };

        ConfirmCallback nackCallBack = (deliveryTag, multiple) ->{
            String message = outstandingConfirms.get(deliveryTag);
            System.out.println("Not been confirmed message is: " +message + " :::tag is"  + deliveryTag);
        };

        /**
         * 1. 监听哪些消息成功了
         * 2. 监听哪些消息失败了
         */

        //异步通知
        channel.addConfirmListener(ackCallBack,nackCallBack);
        long begin = System.currentTimeMillis();
        for(int i = 0; i < MESSAGE_COUNT; i++){
            String message = "message: " + i;
            channel.basicPublish("", queueName, null, message.getBytes());
            //记录已经发送的消息
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
        }
        long end = System.currentTimeMillis();
        System.out.println("publish " + MESSAGE_COUNT + " async  confirm message, time: " + (end - begin) + " ms");

    }


}
