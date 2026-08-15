package com.techilam.order_service.service;

import com.techilam.order_service.dto.OrderItemDto;
import com.techilam.order_service.dto.OrderRequest;
import com.techilam.order_service.model.Order;
import com.techilam.order_service.model.OrderItem;
import com.techilam.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderItemList = orderRequest.getOrderItemDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .toList();

        order.setOrderItemList(orderItemList);

        orderRepository.save(order);
    }

    private OrderItem mapToDto(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .skuCode(orderItemDto.getSkuCode())
                .build();
    }
}
