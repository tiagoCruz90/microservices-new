package com.tutorial.order.service;

import com.tutorial.order.dto.OrderRequestDTO;
import  io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import java.util.List;

public interface OrderService {

    String placeOrder(OrderRequestDTO orderRequestDTO);

    List<OrderRequestDTO> getAllOrders();

    void updateOrder(Long iD, OrderRequestDTO orderRequestDTO);

    }
