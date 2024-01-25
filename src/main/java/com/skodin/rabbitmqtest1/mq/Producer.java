package com.skodin.rabbitmqtest1.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
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
    private String baseRouting;

    @Scheduled(fixedDelay = 5000)
    public void produce() {
        Random random = new Random();
        LocalDate date = LocalDate.now();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");

        Message message = new Message(date.toString().getBytes(), messageProperties);

        boolean b = random.nextBoolean();

        rabbitTemplate.send(exchange, baseRouting + "." + b, message);

        log.info("produce: {}, queue: {}", date, b);
    }

}
