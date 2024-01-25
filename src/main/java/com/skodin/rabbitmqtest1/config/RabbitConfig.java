package com.skodin.rabbitmqtest1.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

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

}
