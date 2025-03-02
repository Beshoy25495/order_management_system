package com.bwagih.orderservice.application.order;

import com.bwagih.orderservice.application.advice.OrderNotFoundException;
import com.bwagih.orderservice.shared.annotations.MeasureExecutionTime;
import com.bwagih.orderservice.shared.Defines;
import com.bwagih.orderservice.infrastructure.RabbitMQPublisher;
import com.bwagih.orderservice.infrastructure.config.RabbitMqOrderConfig;
import com.bwagih.orderservice.presentation.dots.OrderDTO;
import com.bwagih.orderservice.infrastructure.order.mongo.OrderProjection;
import com.bwagih.orderservice.infrastructure.order.mongo.MongoOrderRepository;
import com.bwagih.orderservice.domain.order.Order;
import com.bwagih.orderservice.domain.order.OrderStatus;
import com.bwagih.orderservice.domain.enums.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final MongoOrderRepository orderRepository;
    private final RabbitMQPublisher messagePublisher;
    private final RabbitMqOrderConfig rabbitMqOrderConfig;


    @MeasureExecutionTime
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = getOrderWithUpdatedStatus(orderDTO, OrderStatus.PENDING, SagaStatus.INITIATED);

        Order savedOrder = orderRepository.save(order);

        messagePublisher.publish(rabbitMqOrderConfig.getExchange(), rabbitMqOrderConfig.getRoutingKey(), savedOrder.getOrderId());
        log.info(Defines.Order.ORDER_PUBLISHER_INFO, savedOrder.getOrderId());

        return OrderMapper.INSTANCE.toDTO(savedOrder);
    }

    private static Order getOrderWithUpdatedStatus(OrderDTO orderDTO, OrderStatus status, SagaStatus sagaStatus) {
        Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
        order.setStatus(status);
        order.setSagaStatus(sagaStatus);
        return order;
    }

    public List<OrderProjection> getOrdersByStatus(String status) {
        return orderRepository.findOrdersByStatus(status);
    }

    public OrderProjection getOrderStatus(String orderId) {
        return orderRepository.findOrderStatusByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(Defines.Order.ORDER_NOT_FOUND + orderId));
    }

}
