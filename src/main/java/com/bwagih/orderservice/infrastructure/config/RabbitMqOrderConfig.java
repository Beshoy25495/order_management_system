package com.bwagih.orderservice.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "rabbitmq.order")
public class RabbitMqOrderConfig {
    private String exchange;
    private String queue;
    private String routingKey;
    private String dlqQueue;
    private String dlqRoutingKey;
}
