package com.ljw.rabbitmq.five;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.PriorityQueue;

public class NormalProduct {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        //AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for(int i = 0;i<10;i++){
            channel.basicPublish(NORMAL_EXCHANGE,"normal",null,("message is "+i).getBytes(StandardCharsets.UTF_8));
        }
    }
}
