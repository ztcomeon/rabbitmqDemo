/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: InfoReceiver
 * Author:   Administrator
 * Date:     2020-03-02 14:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.rabbitmqDemo.configuration.rabbitmq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-02
 * @since 1.0.0
 */
//@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.direct.config.queue.error.name}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.direct.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${mq.direct.config.queue.error.routing.key}"
        )
)
public class ErrorReceiver {

        @RabbitHandler
        public void process(String message) {
                System.out.println(".......error........收到的消息:::" + message);
        }
}