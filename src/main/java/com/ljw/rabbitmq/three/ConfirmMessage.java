package com.ljw.rabbitmq.three;


import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 消息的发布确认
 */
public class ConfirmMessage {
    public static final String CONFIRM_MESSAGE = "confirm_message";
    public static void main(String[] args) throws Exception {
        syncBatchConfirmMessage();
    }
    //单个确认模式 一共用时：908
    public static void singleConfirmMessage() throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.confirmSelect();
        channel.queueDeclare(CONFIRM_MESSAGE,false,false,false,null);
        long begin = System.currentTimeMillis();
        for (int i=0;i<1000;i++){
            channel.basicPublish("",CONFIRM_MESSAGE,null,(i+"").getBytes(StandardCharsets.UTF_8));
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息发送成功:  "+i);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("一共用时："+(end-begin));
    }
    //批量确认模式 一共用时：50
    public static void batchConfirmMessage() throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.confirmSelect();
        channel.queueDeclare(CONFIRM_MESSAGE,false,false,false,null);
        long begin = System.currentTimeMillis();
        for (int i=0;i<1000;i++){
            channel.basicPublish("",CONFIRM_MESSAGE,null,(i+"").getBytes(StandardCharsets.UTF_8));
        }
        boolean flag = channel.waitForConfirms();
        System.out.println("消息已发送：");
        long end = System.currentTimeMillis();
        System.out.println("一共用时："+(end-begin));
    }
    // 一部批量确认 一共用时：38
    public static void syncBatchConfirmMessage() throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.confirmSelect();
        ConcurrentSkipListMap<Long,String> map = new ConcurrentSkipListMap();
        channel.addConfirmListener((deliveryTag,multiple)->{
            System.out.println("消息: "+deliveryTag+" 已确认发布！ "+multiple);
            if(multiple){
                ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap = map.headMap(deliveryTag);
                longStringConcurrentNavigableMap.clear();
            }else {
                map.clear();
            }
        },(deliveryTag,multiple)->{
            String m = map.get(deliveryTag);
            System.out.println("消息: "+deliveryTag+" 发布失败" +m);
        });

        channel.queueDeclare(CONFIRM_MESSAGE,false,false,false,null);
        long begin = System.currentTimeMillis();
        for (int i=0;i<1000;i++){
            map.put(channel.getNextPublishSeqNo(),(i+""));
            channel.basicPublish("",CONFIRM_MESSAGE,null,(i+"").getBytes(StandardCharsets.UTF_8));
        }
        long end = System.currentTimeMillis();
        System.out.println("一共用时："+(end-begin));
    }
}
