package com.bwagih.orderservice.application.order;

import com.bwagih.orderservice.application.advice.OrderNotFoundException;
import com.bwagih.orderservice.shared.annotations.MeasureExecutionTime;
import com.bwagih.orderservice.shared.Defines;
import com.bwagih.orderservice.infrastructure.config.RabbitMqOrderConfig;
import com.bwagih.orderservice.infrastructure.order.mongo.MongoOrderRepository;
import com.bwagih.orderservice.domain.order.Order;
import com.bwagih.orderservice.domain.order.OrderStatus;
import com.bwagih.orderservice.domain.enums.SagaStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderProcessor {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessor.class);

    private final MongoOrderRepository orderRepository;
    private final RabbitMqOrderConfig rabbitMqOrderConfig;

    @MeasureExecutionTime
    @RabbitListener(queues = "#{rabbitMqOrderConfig.getQueue()}")
    public void processOrder(String orderId) {
        try {

            Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(Defines.Order.ORDER_NOT_FOUND + orderId));
            saveOrderWithUpdatedStatus(order, OrderStatus.PROCESSING, SagaStatus.IN_PROGRESS);

            simulateProcessing();

            saveOrderWithUpdatedStatus(order, OrderStatus.COMPLETED, SagaStatus.SUCCESS);

        } catch (Exception e) {
            log.error(Defines.Order.ORDER_PROCESSING_ERROR, orderId, e.getMessage());
            rollbackOrder(orderId);
        }
    }


    public void rollbackOrder(String orderId) {
        orderRepository.findById(orderId).ifPresent(order -> saveOrderWithUpdatedStatus(order, OrderStatus.FAILED, SagaStatus.ROLLBACK));
    }


    @Transactional
    public void saveOrderWithUpdatedStatus(Order order, OrderStatus status, SagaStatus sagaStatus) {
        order.setStatus(status);
        order.setSagaStatus(sagaStatus);
        orderRepository.save(order);
    }

    private void simulateProcessing() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
