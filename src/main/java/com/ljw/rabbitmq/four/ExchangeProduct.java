package com.ljw.rabbitmq.four;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ExchangeProduct {
    private static final String EXCHANGE_NAME = "direct_test";
    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.nextLine();
            channel.basicPublish(EXCHANGE_NAME,"haha",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者成功发送");
        }

    }
}
