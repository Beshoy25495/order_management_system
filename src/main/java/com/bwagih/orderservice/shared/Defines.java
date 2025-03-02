package com.bwagih.orderservice.shared;

public final class Defines {

    public static final String UN_EXPECTED_ERROR = "An unexpected error occurred";
    public static final String ORDER_NOT_FOUND = "Order not found";

    public static class MQ {
        public static final String INVALID_MESSAGE = "Message cannot be null";
        public static final String MESSAGE_PUBLISHED = "Message published to exchange: {}, routingKey: {} - {}";
        public static final String MESSAGE_PUBLISH_ERROR = "Error publishing message to exchange: {}, routingKey: {} - {}";
    }

    public static class Order {
        public static final String ORDER_PROCESSING_ERROR = "Error processing order with id: {} - {}";
        public static final String ORDER_PUBLISHER_INFO = "Order with id: {} published to RabbitMQ";
        public static final String ORDER_NOT_FOUND = "Order not found with id: {} ";
    }

}
