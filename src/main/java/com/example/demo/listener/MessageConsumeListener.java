package com.example.demo.listener;

import com.example.demo.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = RabbitMQConfig.DEFAULT_QUEUE_NAME)
@Slf4j
public class MessageConsumeListener {


    @RabbitHandler
    public void consume(@Payload String msg, @Headers Channel channel, Message message) throws IOException {
        log.info("监听器开始消费消息");
            //消费消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 默认处理器，用于处理其他处理器不能处理的消息
     * @param message 源消息
     * @param channel channel
     * @param msg 消息
     */
    @RabbitHandler(isDefault = true)
    public void catchException(@Payload Message message,Channel channel,Message msg){
        try {
            channel.basicNack(msg.getMessageProperties().getDeliveryTag(),false,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}