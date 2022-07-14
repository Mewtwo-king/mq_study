package com.ljw.rabbitmq.five;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PriorityQueueMq {
    private static final String PRIORITY_QUEUE = "priority_queue";
    public static void main(String[] args) throws Exception{
        Channel channel = MqUtil.getChannel();
        Map agr = new HashMap();
        //设置优先级
        agr.put("x-max-priority",10);
        channel.queueDeclare(PRIORITY_QUEUE,false,false,false,agr);
        for (int i=1;i<=10;i++){
            if(i==5){
                AMQP.BasicProperties priority = new AMQP.BasicProperties.Builder().priority(5).build();
                channel.basicPublish("",PRIORITY_QUEUE,priority,("消息是: "+i).getBytes(StandardCharsets.UTF_8));
            }
            channel.basicPublish("",PRIORITY_QUEUE,null,("消息是: "+i).getBytes(StandardCharsets.UTF_8));
        }
    }
}
