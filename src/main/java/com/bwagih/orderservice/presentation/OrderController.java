package com.bwagih.orderservice.presentation;

import com.bwagih.orderservice.presentation.dots.OrderDTO;
import com.bwagih.orderservice.infrastructure.order.mongo.OrderProjection;
import com.bwagih.orderservice.application.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderProjection>> getOrdersByStatus(@PathVariable String status) {
        List<OrderProjection> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{orderId}/status")
    public ResponseEntity<OrderProjection> getOrderStatus(@PathVariable String orderId) {
        OrderProjection orderStatus = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(orderStatus);
    }
}
