package com.ljw.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;

public class Consumer {
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.140.133");
        factory.setUsername("ljw");
        factory.setPassword("1006");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /**
         * 消费者消费消息 String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback
         * 1.消费哪个队列
         * 2.消费之后是否自动答应，ture自动 false 手动答应
         * 3.消费者未成功消费的回调
         * 4.消费者取消消费的回调
         */
        String msg = channel.basicConsume(QUEUE_NAME,true,(consumerTag,message)->{

            System.out.println("c1 消息体是："+new String(message.getBody()));
        },(consumerTag)->{
            System.out.println("消息被中断");
        });
        System.out.println("消息是： "+msg);


    }
}
