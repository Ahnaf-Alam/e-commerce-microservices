package com.techilam.order_service.service;

import com.techilam.order_service.client.InventoryClient;
import com.techilam.order_service.dto.InventoryResponse;
import com.techilam.order_service.dto.OrderItemDto;
import com.techilam.order_service.dto.OrderRequest;
import com.techilam.order_service.model.Order;
import com.techilam.order_service.model.OrderItem;
import com.techilam.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
//    private final WebClient.Builder webclientBuilder;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderItemList = orderRequest.getOrderItemDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .toList();

        order.setOrderItemList(orderItemList);

        List<String> skuCodes = order.getOrderItemList().stream().map(OrderItem::getSkuCode).toList();

        for(String skuCode : skuCodes) {

        }


        // Call inventory service and place order if product is in using spring reactive webclient
        // stock
//        InventoryResponse[] inventoryResponses = webclientBuilder.build().get()
//                .uri("http://inventory-service/api/inventory",
//                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
//                .retrieve()
//                .bodyToMono(InventoryResponse[].class)
//                .block(); // get synchronous call
        
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Products is out of stock");
        }
    }

    private OrderItem mapToDto(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .skuCode(orderItemDto.getSkuCode())
                .build();
    }
}
