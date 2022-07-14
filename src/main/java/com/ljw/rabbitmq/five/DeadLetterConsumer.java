package com.ljw.rabbitmq.five;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;

import java.util.PriorityQueue;

public class DeadLetterConsumer {
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";
    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"dead_key");
        channel.basicConsume(DEAD_QUEUE,true,(tage,message)->{
            System.out.println("死信队列接收到的消息是: "+new String(message.getBody()));
        },(tage)->{});
    }
}
