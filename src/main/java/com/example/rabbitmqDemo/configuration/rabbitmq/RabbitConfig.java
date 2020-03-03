/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: RabbitConfig
 * Author:   Administrator
 * Date:     2020-03-02 11:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.rabbitmqDemo.configuration.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-02
 * @since 1.0.0
 */
//@Configuration
public class RabbitConfig {

    @Bean
    public Queue createQueue(){

        //注意导入的包为：amqp.core.Queue
        return new Queue("hello_queue");

        //此入门队列，为了学习而自己创建的队列，如果指定了队列的名称，rabbitmq会自动创建队列，无需手动创建队列
    }
}