package com.ljw.rabbitmq.four;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;

public class ExchangeConsumer2 {
    private static final String EXCHANGE_NAME = "direct_test";
    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        //声明一个交换机
        // channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //声明一个临时队列、队列名称是随机的
        String queue = channel.queueDeclare().getQueue();
        //绑定交换机与队列
        channel.queueBind(queue,EXCHANGE_NAME,"");
        System.out.println("ExchangeConsumer2 等待接收消息");
        channel.basicConsume(queue,true,(tage,message)->{
            System.out.println("ExchangeConsumer2接收到的消息是:"+ new String(message.getBody()));
        },(consumerTag)->{});

    }
}
