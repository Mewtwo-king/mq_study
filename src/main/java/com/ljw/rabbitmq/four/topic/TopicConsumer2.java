package com.ljw.rabbitmq.four.topic;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class TopicConsumer2 {
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    public static final String CONSUMER2_QUEUE = "consumer2_queue";
    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(CONSUMER2_QUEUE,false,false,false,null);
        channel.queueBind(CONSUMER2_QUEUE,TOPIC_EXCHANGE,"#.ljw");
        System.out.println("TopicConsumer2等待接收消息");
        channel.basicConsume(CONSUMER2_QUEUE,true,(consumerTag,message)->{
            System.out.println("TopicConsumer1接收到的消息是:  "+new String(message.getBody()));
        },(consumerTag)->{});
    }
}
