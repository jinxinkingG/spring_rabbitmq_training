package com.example.demo.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
public class MessageConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    /**
     * Confirmation callback.
     *
     * @param correlationData correlation data for the callback.
     * @param ack             true for ack, false for nack
     * @param cause           An optional cause, for nack, when available, otherwise null.
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(!ack){
            log.info("消息接受失败，原因：" +cause);
        }
        else {
            log.info("消息被成功接收");
        }
    }
}
