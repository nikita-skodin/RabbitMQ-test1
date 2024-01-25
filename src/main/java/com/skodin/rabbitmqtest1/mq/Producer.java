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
import java.util.Random;

@Log4j2
@Component
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${custom.rabbit.exchange}")
    private String exchange;

    @Value("${custom.rabbit.routing}")
    private String routing;

    @Value("${custom.rabbit.first-queue}")
    private String firstQueue;

    @Value("${custom.rabbit.second-queue}")
    private String secondQueue;

    @Bean
    public Queue firstQueue() {
        return new Queue(firstQueue, true);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(secondQueue, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange, true, false);
    }

    @Bean
    public Binding firstBinding(Queue firstQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(firstQueue).to(topicExchange).with(routing + ".true");
    }

    @Bean
    public Binding secondBinding(Queue secondQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(secondQueue).to(topicExchange).with(routing + ".false");
    }

    @Scheduled(fixedDelay = 5000)
    public void produce() {
        Random random = new Random();
        LocalDate date = LocalDate.now();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");

        Message message = new Message(date.toString().getBytes(), messageProperties);

        boolean b = random.nextBoolean();

        rabbitTemplate.send(exchange, routing + "." + b, message);

        log.info("produce: {}, queue: {}", date, b);
    }

}
