package com.example.demo.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public class MessageReturnCallBack implements RabbitTemplate.ReturnsCallback {

    /**
     * Returned message callback.
     *
     * @param returned the returned message and metadata.
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info(returned.getReplyText());
        log.info(returned.getExchange());
        log.info(returned.getRoutingKey());
    }
}