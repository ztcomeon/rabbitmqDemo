/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: RabbitmqDemoController
 * Author:   Administrator
 * Date:     2020-03-02 11:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.rabbitmqDemo.controller;

import com.example.rabbitmqDemo.controller.model.ResponseCode;
import com.example.rabbitmqDemo.controller.model.ResponseModel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-02
 * @since 1.0.0
 */
@RestController
public class RabbitmqDemoController {

    //AmqpTemplate接口定义了发送和接收消息的基本操作,目前spring官方也只集成了Rabbitmq一个消息队列。
    @Autowired
    AmqpTemplate rabbitmqTemplate;

    @Value("${mq.direct.config.exchange}")
    private String directExchange;

    @Value("${mq.direct.config.queue.info.routing.key}")
    private String infoRoutingKey;

    @Value("${mq.direct.config.queue.error.routing.key}")
    private String errorRoutingKey;

    @RequestMapping(value = "send", method = RequestMethod.GET)
    public ResponseModel send(String msg) {

        //发送消息 convertAndSend(队列名称,消息) 入门测试
        rabbitmqTemplate.convertAndSend("hello_queue", msg);
        String data = "消息：" + msg + ",已发送";
        ResponseModel result =
                new ResponseModel(new Date().getTime(), data, ResponseCode._200, "");
        return result;
    }

    /*============测试direct===========================*/

    @RequestMapping(value = "send2", method = RequestMethod.GET)
    public ResponseModel send2(String msg) throws Exception {

        //发送消息 convertAndSend(交换机名称，路由键,消息)
        for (int i = 0; i < 50; i++) {
            Thread.sleep(2000);
            rabbitmqTemplate.convertAndSend(this.directExchange, this.infoRoutingKey, msg);
        }
        String data = "消息：" + msg + ",已发送";
        ResponseModel result =
                new ResponseModel(new Date().getTime(), data, ResponseCode._200, "");
        return result;
    }

    @RequestMapping(value = "send3", method = RequestMethod.GET)
    public ResponseModel send3(String msg) {

        //发送消息 convertAndSend(交换机名称，路由键,消息)
        rabbitmqTemplate.convertAndSend(this.directExchange, this.errorRoutingKey, msg);
        String data = "消息：" + msg + ",已发送";
        ResponseModel result =
                new ResponseModel(new Date().getTime(), data, ResponseCode._200, "");
        return result;
    }

    /*============测试topic===========================*/
    @Value("${mq.topic.config.exchange}")
    private String topicExchange;

    @RequestMapping(value = "send4", method = RequestMethod.GET)
    public ResponseModel send4(String msg) {

        //发送消息 convertAndSend(交换机名称，路由键,消息)
        //用户信息
        rabbitmqTemplate.convertAndSend(this.topicExchange, "user.log.debug", "user.log.debug......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "user.log.info", "user.log.info......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "user.log.error", "user.log.error......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "user.log.warn", "user.log.warn......." + msg);

        //产品信息
        rabbitmqTemplate.convertAndSend(this.topicExchange, "product.log.debug", "product.log.debug......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "product.log.info", "product.log.info......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "product.log.error", "product.log.error......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "product.log.warn", "product.log.warn......." + msg);

        //订单信息
        rabbitmqTemplate.convertAndSend(this.topicExchange, "order.log.debug", "order.log.debug......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "order.log.info", "order.log.info......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "order.log.error", "order.log.error......." + msg);
        rabbitmqTemplate.convertAndSend(this.topicExchange, "order.log.warn", "order.log.warn......." + msg);
        String data = "消息：" + msg + ",已发送";
        ResponseModel result =
                new ResponseModel(new Date().getTime(), data, ResponseCode._200, "");
        return result;
    }

    @Value("${mq.fanout.config.exchange}")
    private String fanoutExchange;

    @RequestMapping(value = "send5", method = RequestMethod.GET)
    public ResponseModel send5(String msg) {
        //没有路由键的消息，第二个参数为空串就可以了
        rabbitmqTemplate.convertAndSend(this.fanoutExchange, "", "user.log.debug......." + msg);
        String data = "消息：" + msg + ",已发送";
        ResponseModel result =
                new ResponseModel(new Date().getTime(), data, ResponseCode._200, "");
        return result;
    }

    @RequestMapping(value = "send6", method = RequestMethod.GET)
    public String send6(Integer exp) {

        //注解版的死信队列

        String msg = "发送时间：" + new Date();

        rabbitmqTemplate.convertAndSend("MQConstants.DELAY_EXCHANGE", "MQConstants.DELAY_KEY", msg, message -> {
            message.getMessageProperties().setDelay(exp);// 单位 毫秒
            return message;
        });
        System.out.println("发送");
        return "---------sendTime:" + new Date();
    }

}