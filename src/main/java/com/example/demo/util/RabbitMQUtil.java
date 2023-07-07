package com.example.demo.util;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.entity.MqMessageEntity;
import com.example.demo.listener.MqListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 直接消费消息
     * @return 消息体
     */
    public Message receiveMessage(){
        return rabbitTemplate.receive(RabbitMQConfig.DEFAULT_QUEUE_NAME);
    }

    public Object receiveNoMessageMsg(){
        return rabbitTemplate.receiveAndConvert(RabbitMQConfig.DEFAULT_QUEUE_NAME);
    }

    public Object receiveMsgAndTransfer(){
        final Object[] result = new Object[1];
        rabbitTemplate.receiveAndReply(RabbitMQConfig.DEFAULT_QUEUE_NAME,payload->payload+"reply",((request, reply) ->{
            log.info(reply);
            result[0] =reply;
            return new Address(RabbitMQConfig.DEAD_EXCHANGE_NAME,RabbitMQConfig.DEAD_ROUTING_KEY);
        }));
        return result[0];
    }

    public Object receiveDeadNoMessageMsg(){
        return rabbitTemplate.receiveAndConvert(RabbitMQConfig.DEAD_QUEUE_NAME);
    }
}
