package com.bwagih.orderservice.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {
    private final ApplicationContext applicationContext;
    private final RabbitMqOrderConfig rabbitMqOrderConfig;

    @Value("${rabbitmq.auto-startup:true}")
    private boolean autoStartup;

    @Value("${rabbitmq.initial-consumer-count:2}")
    private int initialConsumerCount;

    @Value("${rabbitmq.max-consumer-count:10}")
    private int maxConsumerCount;

    @Value("${rabbitmq.ttl:30000}")
    private int ttl;

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setConcurrentConsumers(initialConsumerCount);
        factory.setMaxConcurrentConsumers(maxConsumerCount);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setApplicationContext(applicationContext);
        admin.setAutoStartup(autoStartup);
        return admin;
    }

    @Bean
    public DirectExchange exchange(RabbitAdmin rabbitAdmin) {
        DirectExchange exchange = new DirectExchange(rabbitMqOrderConfig.getExchange());
        rabbitAdmin.declareExchange(exchange);
        return exchange;
    }

    @Bean
    public Queue orderQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = QueueBuilder.durable(rabbitMqOrderConfig.getQueue())
                .ttl(ttl)
                .deadLetterExchange(rabbitMqOrderConfig.getExchange())
                .deadLetterRoutingKey(rabbitMqOrderConfig.getDlqRoutingKey())
                .build();
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue dlqQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = QueueBuilder.durable(rabbitMqOrderConfig.getDlqQueue()).build();
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Binding orderQueueBinding(RabbitAdmin rabbitAdmin, Queue orderQueue, DirectExchange exchange) {
        Binding binding = BindingBuilder.bind(orderQueue).to(exchange).with(rabbitMqOrderConfig.getRoutingKey());
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public Binding dlqBinding(RabbitAdmin rabbitAdmin, Queue dlqQueue, DirectExchange exchange) {
        Binding binding = BindingBuilder.bind(dlqQueue).to(exchange).with(rabbitMqOrderConfig.getDlqRoutingKey());
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
}
