/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TimeoutDLX
 * Author:   Administrator
 * Date:     2020-03-03 17:32
 * Description: 基于代码，测试延迟队列
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.rabbitmqDemo.configuration.rabbitmq2;

import com.example.rabbitmqDemo.configuration.rabbitmq2.RabbitConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈基于代码，测试延迟队列〉
 *     参考：https://blog.csdn.net/dh554112075/article/details/90597649
 * @author Administrator
 * @create 2020-03-03
 * @since 1.0.0
 */
public class TimeoutDLX {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitConnectionFactory.getConnection();
        Channel channel = connection.createChannel();
        //创建DLX及死信队列
        channel.exchangeDeclare("dlx.exchange", "direct");
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "dlx.routingKey");
        //创建测试超时的Exchange及Queue
        channel.exchangeDeclare("delay.exchange", "direct");
        Map<String, Object> arguments = new HashMap<>();
        //过期时间10s
        arguments.put("x-message-ttl", 10000);
        //绑定DLX
        arguments.put("x-dead-letter-exchange", "dlx.exchange");
        //绑定发送到DLX的RoutingKey
        arguments.put("x-dead-letter-routing-key", "dlx.routingKey");
        channel.queueDeclare("delay.queue", true, false, false, arguments);
        channel.queueBind("delay.queue", "delay.exchange", "delay.routingKey");
        //发布一条消息
        channel.basicPublish("delay.exchange", "delay.routingKey", null, "该消息将在10s后发送到延迟队列".getBytes());
        channel.close();
        connection.close();
    }
}