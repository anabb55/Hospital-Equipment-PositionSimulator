package com.ISAPROJEKAT.positionsimulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "api/producer")
public class ProducerController {

    @Autowired
    private Producer producer;

    @PostMapping(value="/{queue}", consumes = "text/plain")
    public ResponseEntity<String> sendMessage(@PathVariable("queue") String queue, @RequestBody String message) {
        producer.sendTo(queue, message);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/{exchange}/{queue}")
    public ResponseEntity<String> sendMessageToExchange(@PathVariable("exchange") String exchange, @PathVariable("queue") String queue, @RequestBody List<Location> StartEndPosition) throws IOException, InterruptedException {
        producer.sendToExchange(exchange, queue, StartEndPosition);
        return ResponseEntity.ok().build();
    }

}
