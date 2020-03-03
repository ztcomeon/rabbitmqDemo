/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: DelayTest
 * Author:   Administrator
 * Date:     2020-03-03 15:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.rabbitmqDemo.configuration.rabbitmq;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-03
 * @since 1.0.0
 */

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@RabbitListener(bindings= @QueueBinding(value = @Queue(value = "MQConstants.DELAY_QUEUE", durable = "true"),
        exchange = @Exchange(value = "MQConstants.DELAY_EXCHANGE",type= ExchangeTypes.DIRECT,
                arguments=@Argument(name="x-delayed-type",value="direct"),delayed=Exchange.TRUE),
        key = "MQConstants.DELAY_KEY"))
public class DelayTest {

    @RabbitHandler
    public void receiveDelay(String msg, Message message, Channel channel) {
        System.out.println(msg);
        System.out.println(message.toString());
        System.out.println(message.getMessageProperties().getReceivedDelay());
        System.out.println("-----------收到消息:"+msg+",当前时间："+new Date());
    }

}
