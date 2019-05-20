package com.hrg.demorabbitmq;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class SendByExchange {

    private final static String QUEUE_NAME = "q_test_01";
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        //channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 消息内容
        String message = "Hello World!";
        
      //basicPublish第一个参数为交换机名称、
        // 第二个参数为队列映射的路由key、
        // 第三个参数为消息的其他属性、
        // 第四个参数为发送信息的主体
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}