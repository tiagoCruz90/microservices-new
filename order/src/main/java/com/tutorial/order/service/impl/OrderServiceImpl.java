package com.tutorial.order.service.impl;


import com.tutorial.order.domain.Order;
import com.tutorial.order.domain.OrderLineItems;
import com.tutorial.order.dto.InventoryResponseDTO;
import com.tutorial.order.dto.OrderLineItemsDTO;
import com.tutorial.order.dto.OrderRequestDTO;
import com.tutorial.order.repository.OrderRepository;
import com.tutorial.order.service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


@RequiredArgsConstructor
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Override
    public void placeOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequestDTO.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //Call inventory service to check the availability of the product
       InventoryResponseDTO[] inventoryResponseDTOS =  webClient.get()
                .uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder
                        .queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponseDTO[].class)
                .block(); //block will give a synchronous call

        assert inventoryResponseDTOS != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponseDTOS)
                    .allMatch(InventoryResponseDTO::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else
            throw new IllegalArgumentException("Product is not available, try again later");
    }

    @Override
    public List<OrderRequestDTO> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();

        return orderList.stream()
                .map(this::mapToOrderRequestDTO)
                .toList();
    }

    @Override
    public void updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequestDTO.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(order);
    }


    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        return orderLineItems;
    }

    private OrderRequestDTO mapToOrderRequestDTO(Order order) {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();

        List<OrderLineItemsDTO> orderLineItemsDTOList = order.getOrderLineItemsList()
                .stream()
                .map(this::mapToOrderLineItemsDTO)
                .toList();

        orderRequestDTO.setOrderLineItemsDTOList(orderLineItemsDTOList);
        return orderRequestDTO;
    }

    private OrderLineItemsDTO mapToOrderLineItemsDTO(OrderLineItems orderLineItems) {
        OrderLineItemsDTO orderLineItemsDTO = new OrderLineItemsDTO();
        orderLineItemsDTO.setId(orderLineItems.getId());
        orderLineItemsDTO.setSkuCode(orderLineItems.getSkuCode());
        orderLineItemsDTO.setQuantity(orderLineItems.getQuantity());
        orderLineItemsDTO.setPrice(orderLineItems.getPrice());
        return orderLineItemsDTO;
    }
}
