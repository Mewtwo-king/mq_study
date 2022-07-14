package com.ljw.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

/**
 * 发消息
 */
public class Producer {
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.140.133");
        factory.setUsername("ljw");
        factory.setPassword("1006");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //String queue , boolean durable 是否持久化，保存到磁盘，默认存在内存中, b oolean exclusive 是否进行消息共享，ture多个消费者共享,
        // boolean autoDelete 是否自动删除 map 其它参数
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        String message = "hello world";
        /**
         * String exchange, String routingKey, BasicProperties props, byte[] body
         * 1.发送到哪个交换机 2.路由的key值是哪个，本次是队列的名称 3.其他参数信息 4.消息体
         */
        channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
        System.out.println("消息发送完毕!");


    }
}
