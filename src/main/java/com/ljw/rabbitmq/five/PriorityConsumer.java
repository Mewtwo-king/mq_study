package com.ljw.rabbitmq.five;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;

public class PriorityConsumer {
    private static final String PRIORITY_QUEUE = "priority_queue";
    public static void main(String[] args) throws Exception{
        Channel channel = MqUtil.getChannel();
        String msg = channel.basicConsume(PRIORITY_QUEUE,true,(consumerTag,message)->{

            System.out.println("c1 消息体是："+new String(message.getBody()));
        },(consumerTag)->{
            System.out.println("消息被中断");
        });
    }
}
