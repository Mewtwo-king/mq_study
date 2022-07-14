package com.ljw.rabbitmq.two;


import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * 采用手动答应模式
 */
public class WorkAutoAsk {
    public static final String TASK_MESSAGE = "task";
    public static void main(String[] args) throws Exception{
        new Thread(()->{
            try {
                Channel channel = MqUtil.getChannel();
                channel.basicQos(2);
                channel.basicConsume(TASK_MESSAGE,false,(tag,message)->{
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t1得到消息: "+new String(message.getBody()));
                    //multiple 表示是否批量答应x
                    channel.basicQos(1);
                    channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                    System.out.println("t1完成消息 已经答应!");
                },(tag)->{
                    System.out.println("t1消息消费失败");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                Channel channel = MqUtil.getChannel();
                channel.basicQos(3);
                channel.basicConsume(TASK_MESSAGE,false,(tag,message)->{
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t2得到消息: "+new String(message.getBody()));
                    channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                    //channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
                    //channel.basicNack(message.getEnvelope().getDeliveryTag(),false,true);
                    System.out.println("t2完成消息 已经答应!");
                },(tag)->{
                    System.out.println("t2消息消费失败！");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
