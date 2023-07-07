package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    //默认交换机
    public static final String DEFAULT_EXCHANGE_NAME = "julian-default-exchange";
    //默认队列
    public static final String DEFAULT_QUEUE_NAME = "julian-default-queue";

    //死信交换机
    public static final String DEAD_EXCHANGE_NAME = "julian-dead-exchange";

    //死信队列
    public static final String DEAD_QUEUE_NAME = "julian-dead-queue";

    //死信路由key
    public static final String DEAD_ROUTING_KEY = "julian-dead-routing-key";

    //主题交换机
    public static final String TOPIC_EXCHANGE_NAME = "julian-topic-exchange";

    //扇出交换机
    public static final String FANOUT_EXCHANGE_NAME = "julian-fanout-exchange";

    @Bean
    public Exchange buildDefaultExchange(){
        return ExchangeBuilder.directExchange(DEFAULT_EXCHANGE_NAME).durable(true).build();
    }
    @Bean
    public Exchange buildDirectExchange(){
        return ExchangeBuilder.directExchange("").durable(true).build();
    }

    @Bean
    public Exchange buildDeadExchange(){
        return ExchangeBuilder.directExchange(DEAD_EXCHANGE_NAME).durable(true).build();
    }

    @Bean
    public Binding bindDeadExchangeAndQueue(Exchange buildDeadExchange,Queue buildDeadQueue){
        return BindingBuilder.bind(buildDeadQueue).to(buildDeadExchange).with(DEAD_ROUTING_KEY).noargs();
    }

    @Bean
    public Binding bindDefaultExchangeAndQueue(Exchange buildDefaultExchange,Queue buildDefaultQueue){
        return BindingBuilder.bind(buildDefaultQueue).to(buildDefaultExchange).with("").noargs();
    }

    @Bean
    public Queue buildDeadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE_NAME).build();
    }

    @Bean
    public Queue buildDefaultQueue(){
        return QueueBuilder.durable(DEFAULT_QUEUE_NAME).deadLetterExchange(DEAD_EXCHANGE_NAME).deadLetterRoutingKey(DEAD_ROUTING_KEY).build();
    }

    @Bean
    public Exchange buildTopicExchange(){
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME).durable(true).build();
    }

    @Bean
    public Exchange buildFanOutExchange(){
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_NAME).durable(true).build();
    }

}
