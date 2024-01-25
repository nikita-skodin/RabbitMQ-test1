package com.skodin.rabbitmqtest1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitMqTest1Application {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqTest1Application.class, args);
    }

}
