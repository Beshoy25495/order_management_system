package com.bwagih.orderservice.domain;

public interface MessagePublisher {
    void publish(String exchange, String routingKey, Object message);
}
