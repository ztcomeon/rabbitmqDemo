/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: RabbitConnectionFactory
 * Author:   Administrator
 * Date:     2020-03-03 17:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.rabbitmqDemo.configuration.rabbitmq2;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 〈一句话功能简述〉<br>
 * 使用ConnectionFactory，进行延迟队列的测试
 *  参考：https://blog.csdn.net/dh554112075/article/details/90597649
 * @author Administrator
 * @create 2020-03-03
 * @since 1.0.0
 */
public class RabbitConnectionFactory {

    private static final String IP_ADDRESS = "127.0.0.1";

    private static final int PORT = 5672;

    private static final String USERNAME = "guest";

    private static final String PASSWORD = "guest";

    private static ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


}