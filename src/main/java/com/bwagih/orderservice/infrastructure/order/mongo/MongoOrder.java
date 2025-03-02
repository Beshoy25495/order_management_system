package com.bwagih.orderservice.infrastructure.order.mongo;

import com.bwagih.orderservice.domain.order.Order;
import com.bwagih.orderservice.domain.order.OrderStatus;
import com.bwagih.orderservice.domain.enums.SagaStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "orders")
public class MongoOrder {
    @Id
    @Builder.Default
    private String orderId = UUID.randomUUID().toString();
    private String customerName;
    private String productName;
    private Long quantity;
    private OrderStatus status;
    private SagaStatus sagaStatus;

    public static MongoOrder fromDomain(Order order) {
        if (order == null) {
            return null;
        }

        return MongoOrder.builder()
                .orderId(order.getOrderId())
                .customerName(order.getCustomerName())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .sagaStatus(order.getSagaStatus())
                .build();
    }

    public static Order toDomain(MongoOrder mongoOrder) {
        if (mongoOrder == null) {
            return null;
        }
        return Order.builder()
                .orderId(mongoOrder.getOrderId())
                .customerName(mongoOrder.getCustomerName())
                .productName(mongoOrder.getProductName())
                .quantity(mongoOrder.getQuantity())
                .status(mongoOrder.getStatus())
                .sagaStatus(mongoOrder.getSagaStatus())
                .build();
    }
}
