package com.skodin.rabbitmqtest1.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Log4j2
@Component
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${custom.rabbit.exchange}")
    private String exchange;

    @Value("${custom.rabbit.routing}")
    private String routing;

    @Value("${custom.rabbit.queue}")
    private String queue;

    @Bean
    public Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Binding binding1(Queue queue, DirectExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(routing);
    }

    @Scheduled(fixedDelay = 5000)
    public void produce() {
        LocalDate date = LocalDate.now();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");

        Message message = new Message(date.toString().getBytes(), messageProperties);

        rabbitTemplate.send(exchange, routing, message);

        log.info("produce: {}", date);
    }

}
