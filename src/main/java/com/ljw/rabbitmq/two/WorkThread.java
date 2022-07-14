package com.ljw.rabbitmq.two;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
//多个线程轮训方式
public class WorkThread {
    public static final String TASK_MESSAGE = "task";
    public static void main(String[] args) throws Exception{
        Channel channel = MqUtil.getChannel();
        channel.basicQos(1);
        new Thread(()->{
            try {
                channel.basicConsume(TASK_MESSAGE,true,(consumerTag,message)->{
                    System.out.println(" t1得到消息："+new String(message.getBody()));
                    System.out.println("consumerTag is : "+consumerTag);
                    System.out.println("consumerTag is : "+message.getEnvelope().getDeliveryTag());
                },(consumerTag)->{
                    System.out.println("t2消息消费失败: "+consumerTag);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(()->{
            try {
                channel.basicConsume(TASK_MESSAGE,true,(consumerTag,message)->{
                    System.out.println(" t2得到消息："+new String(message.getBody()));
                    System.out.println("consumerTag is : "+consumerTag);
                },(consumerTag)->{
                    System.out.println("t2消息消费失败: "+consumerTag);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();

    }
}
