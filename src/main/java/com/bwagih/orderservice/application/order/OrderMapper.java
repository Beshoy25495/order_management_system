package com.bwagih.orderservice.application.order;

import com.bwagih.orderservice.presentation.dots.OrderDTO;
import com.bwagih.orderservice.domain.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toEntity(OrderDTO dto);

    @Mappings({
            @Mapping(target = "productName", source ="entity.productName")
    })
    OrderDTO toDTO(Order entity);
}
