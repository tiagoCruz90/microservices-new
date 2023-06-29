package com.tutorial.order.service;

import com.tutorial.order.domain.Order;
import com.tutorial.order.dto.OrderRequestDTO;

import java.util.List;

public interface OrderService {

    void placeOrder(OrderRequestDTO orderRequestDTO);

    List<OrderRequestDTO> getAllOrders();

    void updateOrder(Long iD, OrderRequestDTO orderRequestDTO);
}
