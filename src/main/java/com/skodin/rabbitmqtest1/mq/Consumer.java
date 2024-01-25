package com.skodin.rabbitmqtest1.mq;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Log4j2
@Component
public class Consumer {

    @RabbitListener(queues = "${custom.rabbit.first-queue}")
    public void firstConsume(String message) {
        LocalDate date = LocalDate.parse(message);
        log.info("consume from 1 (true): {}", date);
    }

    @RabbitListener(queues = "${custom.rabbit.second-queue}")
    public void secondConsume(String message) {
        LocalDate date = LocalDate.parse(message);
        log.info("consume from 2 (false): {}", date);
    }

}
