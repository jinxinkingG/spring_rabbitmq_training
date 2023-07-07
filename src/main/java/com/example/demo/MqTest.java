package com.example.demo;

import com.example.demo.util.RabbitMQUtil;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MqTest {

    @Autowired
    RabbitMQUtil rabbitMQUtil;

    @Test
    void testSend(){
        MessageProperties build = MessagePropertiesBuilder.newInstance().setExpiration("1000").build();
        MessageProperties build2 = MessagePropertiesBuilder.newInstance().setCorrelationId("jinxin").build();
        String messageString = "hello lalalala";
        byte[] body = messageString.getBytes();
        Message message = new Message(body,build2);
        //rabbitMQUtil.sendMsg(message);
        for(int i=1;i<11;i++){
            rabbitMQUtil.sendNoMessageMsg("hello"+i);
        }
    }

    @Test
    void testReceive(){
        Object message = rabbitMQUtil.receiveMsgAndTransfer();

        System.out.println(message);
        Object messageReReceive = rabbitMQUtil.receiveNoMessageMsg();
        System.out.println(messageReReceive);
        Object o = rabbitMQUtil.receiveDeadNoMessageMsg();
        System.out.println(o);
    }


}
