package com.example.demo.util;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.util.processor.MessageBindCorrelationIdProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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

    public void setConfirmCallback(RabbitTemplate.ConfirmCallback callback){
        try{
            rabbitTemplate.setConfirmCallback(callback);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void setReturnCallback(RabbitTemplate.ReturnsCallback callback){
        try{
            rabbitTemplate.setReturnsCallback(callback);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * 发送消息非Message类型的消息
     * @param msg 消息
     * @param <T> 类型
     */
    public <T> void sendNoMessageMsg(T msg){
        sendNoMessageMsg(msg,null);
    }

    /**
     * 发送消息非Message类型的消息
     * @param msg 消息
     * @param <T> 类型
     * @param data 关联数据
     */
    public <T> void sendNoMessageMsg(T msg, CorrelationData data){
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEFAULT_EXCHANGE_NAME,"", msg,new MessageBindCorrelationIdProcessor(),data);
    }

    /**
     * 发送Message类型的消息
     * @param msg 消息
     */
    public void sendMessage(Message msg){
        sendMessage(msg,null);
    }

    /**
     * 发送Message类型的消息
     * @param msg 消息
     * @param data 关联数据
     */
    public void sendMessage(Message msg,CorrelationData data){
        rabbitTemplate.send(RabbitMQConfig.DEFAULT_EXCHANGE_NAME,"",msg, data);
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
     */
    public void receiveMsgAndTransfer(){
        receiveMsgAndTransfer(RabbitMQConfig.DEAD_EXCHANGE_NAME, RabbitMQConfig.DEAD_ROUTING_KEY);
    }

    /**
     * 消费消息并转发消息至
     *
     * @param exchangeName 交换机名称
     * @param routingKey   路由key
     */
    public void receiveMsgAndTransfer(String exchangeName, String routingKey){
        rabbitTemplate.receiveAndReply(RabbitMQConfig.DEFAULT_QUEUE_NAME, payload -> payload + "reply", ((request, reply) ->
                new Address(exchangeName, routingKey)
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
    public Object receiveDeadMsg(){
        return rabbitTemplate.receiveAndConvert(RabbitMQConfig.DEAD_QUEUE_NAME);
    }

    public <T> Object sendAndReceive(String exchangeName,String routingKey,T msg,CorrelationData data) {
        return rabbitTemplate.convertSendAndReceive(exchangeName,routingKey,msg,new MessageBindCorrelationIdProcessor(),data);
    }

    public <T> Object sendAndReceiveWithDefaultConfig(T msg) {
        return rabbitTemplate.convertSendAndReceive(RabbitMQConfig.DEFAULT_EXCHANGE_NAME, "",msg);
    }
}