package com.tutorial.order.controller;

import com.tutorial.order.dto.OrderRequestDTO;
import com.tutorial.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallback")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> createOrder(@RequestBody OrderRequestDTO orderRequestDTO  ) {
       return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequestDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderRequestDTO> getAllOrders(){
      return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO){
      orderService.updateOrder(id, orderRequestDTO);
      return "Order updated successfully";
    }

   /* public CompletableFuture<String> placeOrderFallback(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Order creation failed. Please try again later");
    }*/
}
