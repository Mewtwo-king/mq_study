package com.ljw.rabbitmq.four.topic;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TopicProduct {
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.basicPublish(TOPIC_EXCHANGE,"ljw.change.lzl",null,"TopicConsumer1 message hello".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(TOPIC_EXCHANGE,"lx.hello.ljw",null,"TopicConsumer2 message haha".getBytes(StandardCharsets.UTF_8));

    }
}
