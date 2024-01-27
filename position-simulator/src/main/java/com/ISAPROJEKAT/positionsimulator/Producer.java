package com.ISAPROJEKAT.positionsimulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    /*
     * RabbitTemplate je pomocna klasa koja uproscava sinhronizovani
     * pristup RabbitMQ za slanje i primanje poruka.
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //routingKey je naziv queue, ovde nemamo naveden exchange pa se smatra da imamo default exchange
    public void sendTo(String routingkey, String message){
        log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[" + routingkey + "]");
        this.rabbitTemplate.convertAndSend(routingkey, message);
    }



     // exchange ce rutirati poruke u pravi queue
    public void sendToExchange(String exchange, String routingkey, String message){
        log.info("Sending> ... Message=[ " + message + " ] Exchange=[" + exchange + "] RoutingKey=[" + routingkey + "]");
        this.rabbitTemplate.convertAndSend(exchange, routingkey, message);
    }
}