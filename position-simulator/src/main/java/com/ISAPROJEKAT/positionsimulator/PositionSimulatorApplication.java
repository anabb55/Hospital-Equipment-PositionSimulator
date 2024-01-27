package com.ISAPROJEKAT.positionsimulator;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PositionSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PositionSimulatorApplication.class, args);
	}

	@Value("${myqueue}")
	String queue;

	@Value("${myqueue2}")
	String queue2;

	@Value("${myexchange}")
	String exchange;

	@Value("${routingkey}")
	String routingkey;


	//kad se ova metoda pozove stvara se instanca Queue obj
	@Bean
	Queue queue() {
		return new Queue(queue, true); //prvi parametar je naziv reda koji se nalazi u varijabli queue, drugi parametar govori da li je red trajan tj da li opstaje posle ponovnog pokretanja app
	}

	@Bean
	Queue queue2() {
		return new Queue(queue2, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue2, DirectExchange exchange) {
		return BindingBuilder.bind(queue2).to(exchange).with(routingkey);
	}

	/*
	 * Registrujemo bean koji ce sluziti za konekciju na RabbitMQ gde se mi u
	 * primeru kacimo u lokalu.
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}
}
