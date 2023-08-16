package com.example.demo.util.processor;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;

public class MessageBindCorrelationIdProcessor implements MessagePostProcessor {
    /**
     * Change (or replace) the message.
     *
     * @param message the message.
     * @return the message.
     * @throws AmqpException an exception.
     */
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        return message;
    }

    /**
     * Change (or replace) the message and/or change its correlation data. Only applies to
     * outbound messages.
     *
     * @param message     the message.
     * @param correlation the correlation data.
     * @return the message.
     * @since 1.6.7
     */
    @Override
    public Message postProcessMessage(Message message, Correlation correlation) {
        if(correlation instanceof CorrelationData){
            message.getMessageProperties().setCorrelationId(((CorrelationData) correlation).getId());
            return postProcessMessage(message);
        }
        return MessagePostProcessor.super.postProcessMessage(message, correlation);
    }

    /**
     * Change (or replace) the message and/or change its correlation data. Only applies to
     * outbound messages.
     *
     * @param message     the message.
     * @param correlation the correlation data.
     * @param exchange    the exchange to which the message is to be sent.
     * @param routingKey  the routing key.
     * @return the message.
     * @since 2.3.4
     */
    @Override
    public Message postProcessMessage(Message message, Correlation correlation, String exchange, String routingKey) {
        return MessagePostProcessor.super.postProcessMessage(message, correlation, exchange, routingKey);
    }
}
