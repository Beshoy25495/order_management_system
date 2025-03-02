package com.bwagih.orderservice.domain.order;

import com.bwagih.orderservice.application.order.OrderMapper;
import com.bwagih.orderservice.domain.enums.SagaStatus;
import com.bwagih.orderservice.presentation.dots.OrderDTO;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private String orderId;

    private String customerName;
    private String productName;
    private Long quantity;

    private OrderStatus status;
    private SagaStatus sagaStatus;

}
