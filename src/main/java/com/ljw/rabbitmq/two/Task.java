package com.ljw.rabbitmq.two;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Task {
    public static final String TASK_MESSAGE = "task";
    public static void main(String[] args) throws Exception{
        Channel channel = MqUtil.getChannel();
        channel.confirmSelect();
        //声明一个队列
        channel.queueDeclare(TASK_MESSAGE,false,false,false,null);
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入需要发送的消息:  ");
        while (scanner.hasNext()){
            String msg = scanner.nextLine();
            channel.basicPublish("",TASK_MESSAGE,false, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}
