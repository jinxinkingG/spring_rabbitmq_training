package com.example.demo.util;

import com.example.demo.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author 巨联开发平台组
 * @version v3.1.0
 * @copyright 巨联数字科技有限公司
 * @date 2023/6/28 16:15
 */
@Component
@Slf4j
public class RabbitMQUtil {

    @Resource
    RabbitTemplate rabbitTemplate;

    /**
     * 发送消息非Message类型的消息
     * @param msg 消息
     * @param <T> 类型
     */
    public <T> void sendNoMessageMsg(T msg){
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEFAULT_EXCHANGE_NAME,"", msg);
    }

    /**
     * 发送Message类型的消息
     * @param msg
     */
    public void sendMessage(Message msg){
        rabbitTemplate.send(RabbitMQConfig.DEFAULT_EXCHANGE_NAME,"",msg);
    }

    /**
     * 默认消费默认队列
     * 直接消费消息
     * @return 消息体
     */
    public Message receiveMessage(){
        return receiveMessage(RabbitMQConfig.DEFAULT_QUEUE_NAME);
    }

    /**
     * 直接消费消息
     * @param queueName 队列名称
     * @return 消息体
     */
    public Message receiveMessage(String queueName){
        return rabbitTemplate.receive(queueName);
    }

    /**
     * 默认取默认队列
     * 直接消费消息
     * @return 消息体
     */
    public Object receiveNoMessageMsg(){
        return receiveNoMessageMsg(RabbitMQConfig.DEFAULT_QUEUE_NAME);
    }

    /**
     * 直接消费消息
     * @param queueName 队列名称
     * @return 消息体
     */
    public Object receiveNoMessageMsg(String queueName){
        return rabbitTemplate.receiveAndConvert(queueName);
    }

    /**
     * 默认转发至死信
     * 消费消息并回复消息
     * @return 状态
     */
    public boolean receiveMsgAndTransfer(){
        return receiveMsgAndTransfer(RabbitMQConfig.DEAD_EXCHANGE_NAME,RabbitMQConfig.DEAD_ROUTING_KEY);
    }

    /**
     * 消费消息并转发消息至
     * @param exchangeName 交换机名称
     * @param routingKey 路由key
     * @return 状态
     */
    public boolean receiveMsgAndTransfer(String exchangeName,String routingKey){
        return rabbitTemplate.receiveAndReply(RabbitMQConfig.DEFAULT_QUEUE_NAME,payload->payload+"reply",((request, reply) ->
                new Address(exchangeName,routingKey)
        ));
    }

    /**
     * 消费死信消息
     * @return 消息体
     */
    public Object receiveDeadNoMessageMsg(){
        return rabbitTemplate.receiveAndConvert(RabbitMQConfig.DEAD_QUEUE_NAME);
    }

    /**
     * 消费死信消息
     * @return 消息体
     */
    public Message receiveDeadMsg(){
        return rabbitTemplate.receive(RabbitMQConfig.DEAD_QUEUE_NAME);
    }
}