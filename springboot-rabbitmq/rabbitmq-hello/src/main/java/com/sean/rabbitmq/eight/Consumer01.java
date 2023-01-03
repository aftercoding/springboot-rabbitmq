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
 * Consumer 1
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2023-01-02 16:37
 */
public class Consumer01 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";

    public static final String  NORMAL_QUEUE = "normal_queue";
    public static final String  DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明dead message 和普通交换机 类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明dead queue and normal queue
        Map<String, Object> arguments = new HashMap<>();
        /**
         * 1. 过期时间
         * 2. 正常队列设置死信交换机
         */
        // 设置ttl, 除了在arguments 里面设置，也可以在生产者那里设置。
//        arguments.put("x-message-ttl", 10000);
        //正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //设置死信 RoutingKey
        arguments.put("x-dead-letter-routing-key", "lisi");
        //设置正常队列的长度限制
//        arguments.put("x-max-length",6);

        //将arguments map 作为参数加入到channel queue declare中
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        //绑定普通的交换机与普通队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        //绑定死信的交换机与死信的队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        System.out.println("waiting for receive message:");

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            String msg = new String(message.getBody(), "UTF-8");
            if(msg.equals("info 5")){
                System.out.println("Consumer01 received message: " + msg + " which is been rejected");
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);//false, means don't resend this message to queue
            }else {
                System.out.println("Consumer01 received message: " + msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        };
        //开启手动应答，而不能是自动应答
        channel.basicConsume(NORMAL_QUEUE,false, deliverCallback, consumerTag ->{});
    }
}
