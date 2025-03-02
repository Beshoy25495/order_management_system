package com.bwagih.orderservice.infrastructure.order.mongo;

import com.bwagih.orderservice.domain.order.Order;
import com.bwagih.orderservice.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoOrderRepository implements OrderRepository<String> {
    private final SpringDataMongoOrderRepository repository;

    @Override
    public Order save(Order entity) {
        return MongoOrder.toDomain(repository.save(MongoOrder.fromDomain(entity)));
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return repository.findById(orderId).map(MongoOrder::toDomain);
    }

    @Override
    public List<OrderProjection> findOrdersByStatus(String status) {
        return repository.findOrdersByStatus(status);
    }

    @Override
    public Optional<OrderProjection> findOrderStatusByOrderId(String orderId) {
        return repository.findOrderStatusByOrderId(orderId);
    }
}

