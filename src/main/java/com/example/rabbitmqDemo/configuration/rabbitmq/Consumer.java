/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Consumer
 * Author:   Administrator
 * Date:     2020-03-02 11:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.rabbitmqDemo.configuration.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-02
 * @since 1.0.0
 */

//@Component
//@RabbitListener(queues = "hello_queue")
public class Consumer {

    //@RabbitListener这个注解可以定义在类上，也可以定义在方法上

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitHandler
    public void process(String message) {
        System.out.println(message);
        logger.info("接收的消息为: {}", message);
    }
}