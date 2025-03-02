package com.bwagih.orderservice.application.order;

import com.bwagih.orderservice.domain.order.Order;
import com.bwagih.orderservice.presentation.dots.OrderDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-02T19:23:46+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toEntity(OrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.orderId( dto.getOrderId() );
        order.customerName( dto.getCustomerName() );
        order.productName( dto.getProductName() );
        order.quantity( (long) dto.getQuantity() );

        return order.build();
    }

    @Override
    public OrderDTO toDTO(Order entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDTO.OrderDTOBuilder orderDTO = OrderDTO.builder();

        orderDTO.productName( entity.getProductName() );
        orderDTO.orderId( entity.getOrderId() );
        orderDTO.customerName( entity.getCustomerName() );
        if ( entity.getQuantity() != null ) {
            orderDTO.quantity( entity.getQuantity().intValue() );
        }

        return orderDTO.build();
    }
}
