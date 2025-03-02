package com.bwagih.orderservice.infrastructure.order.mongo;

public interface OrderProjection {
    String getOrderId();
    String getCustomerName();
    String getProductName();
    String getStatus();
}
