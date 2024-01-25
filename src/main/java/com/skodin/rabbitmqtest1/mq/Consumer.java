package com.skodin.rabbitmqtest1.mq;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Log4j2
@Component
public class Consumer {

    @RabbitListener(queues = "${custom.rabbit.queue}")
    public void consume(String message) {
        LocalDate date = LocalDate.parse(message);
        log.info("consume: {}", date);
    }

}
