package com.hrg.demorabbitmq;
import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RecvByExchange {

    private final static String QUEUE_NAME = "q_test_01";
    
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
     // 	绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        
     // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);


        // 定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel){
            /**
             * envelope主要存放生产者相关信息（比如交换机、路由key等）body是消息实体。
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
            }
        };

        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}