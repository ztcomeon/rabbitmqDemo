/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: DelayExchangeDemo
 * Author:   Administrator
 * Date:     2020-03-03 17:51
 * Description: 基于插件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.rabbitmqDemo.configuration.rabbitmq2;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈基于插件〉
 *
 * @author Administrator
 * @create 2020-03-03
 * @since 1.0.0
 */
public class DelayExchangeDemo {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitConnectionFactory.getConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> arguments = new HashMap<>();

        //定义exchange的x-delayed-type类型
        arguments.put("x-delayed-type", "direct");

        //定义exchange和queue，再进行绑定
        channel.exchangeDeclare("delay.plugin.exchange", "x-delayed-message", true, false, arguments);
        channel.queueDeclare("delay.plugin.queue", true, false, false, null);
        channel.queueBind("delay.plugin.queue", "delay.plugin.exchange", "delay.plugin.routingKey");
        Map<String, Object> headers = new HashMap<>();
        //延迟10s后发送
        headers.put("x-delay", 10000);

        //builder...需要查一下?
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.headers(headers);

        //发送消息
        channel.basicPublish("delay.plugin.exchange", "delay.plugin.routingKey", builder.build(), "该消息将在10s后发送到队列".getBytes());
        channel.close();
        connection.close();
    }

}