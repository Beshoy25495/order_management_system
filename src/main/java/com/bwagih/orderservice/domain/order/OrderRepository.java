package com.bwagih.orderservice.domain.order;

import com.bwagih.orderservice.infrastructure.order.mongo.OrderProjection;

import java.util.List;
import java.util.Optional;

public interface OrderRepository<ID> {

    Order save(Order entity);
    Optional<Order> findById(ID orderId);
    List<OrderProjection> findOrdersByStatus(String status);
    Optional<OrderProjection> findOrderStatusByOrderId(ID orderId);
}
