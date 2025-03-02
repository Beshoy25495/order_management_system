package com.bwagih.orderservice.infrastructure.order.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoOrderRepository extends MongoRepository<MongoOrder, String> {
    @Query(value = "{ 'status': ?0 }", fields = "{ 'orderId': 1, 'customerName': 1, 'status': 1 }")
    List<OrderProjection> findOrdersByStatus(String status);

    @Query(value = "{ 'orderId': ?0 }", fields = "{ 'orderId': 1, 'status': 1, 'customerName': 1, 'productName': 1 }")
    Optional<OrderProjection> findOrderStatusByOrderId(String orderId);
}