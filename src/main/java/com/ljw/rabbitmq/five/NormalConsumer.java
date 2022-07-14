package com.ljw.rabbitmq.five;

import com.ljw.rabbitmq.util.MqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

public class NormalConsumer {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_QUEUE";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);
        Map<String, Object> arguments = new HashMap<>();
        // {@Argument(name="x-dead-letter-exchange",value = "deadExchange"),
        //   @Argument(name="x-dead-letter-routing-key",value = "deadKey"),
        // @Argument(name = "x-message-ttl",value = "3000")
        //x-max-length
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","dead_key");
        arguments.put("x-max-length",6);
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"normal");
        channel.basicConsume(NORMAL_QUEUE,true,(tage,message)->{
            System.out.println("正常队列接收到的消息是: "+new String(message.getBody()));
        },(tage)->{});
    }
}
