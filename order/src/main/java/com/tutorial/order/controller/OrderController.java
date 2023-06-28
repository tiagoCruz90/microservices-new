package com.tutorial.order.controller;

import com.tutorial.order.dto.OrderRequestDTO;
import com.tutorial.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequestDTO orderRequestDTO  ) {
        orderService.placeOrder(orderRequestDTO);
        return "Order created successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderRequestDTO> getAllOrders(){
      return orderService.getAllOrders();
    }

    @PutMapping("/{Id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateOrder(@PathVariable Long Id, @RequestBody OrderRequestDTO orderRequestDTO){
      orderService.updateOrder(Id, orderRequestDTO);
      return "Order updated successfully";
    }
}
