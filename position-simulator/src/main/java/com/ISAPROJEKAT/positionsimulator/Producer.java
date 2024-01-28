package com.ISAPROJEKAT.positionsimulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private LocationService locationService;

    //routingKey je naziv queue, ovde nemamo naveden exchange pa se smatra da imamo default exchange
    public void sendTo(String routingkey, String message){
        log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[" + routingkey + "]");
        this.rabbitTemplate.convertAndSend(routingkey, message);
    }



     // exchange ce rutirati poruke u pravi queue
    public void sendToExchange(String exchange, String routingkey, List<Location> StartEndPosition) throws IOException, InterruptedException {
        log.info("Sending> ... Message=[ " +  StartEndPosition.get(0).getLatitude() +  StartEndPosition.get(0).getLongitude()+" ] Exchange=[" + exchange + "] RoutingKey=[" + routingkey + "]");
       List<Location> locations = new ArrayList<>();
       locations.add(StartEndPosition.get(0));
       locations.addAll(locationService.generateRoute(StartEndPosition.get(0), StartEndPosition.get(1)));
       locations.add(StartEndPosition.get(1));



        for (Location location : locations) {
            System.out.println("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
            this.rabbitTemplate.convertAndSend(exchange, routingkey, location);

            Thread.sleep(2000);
        }



    }
}