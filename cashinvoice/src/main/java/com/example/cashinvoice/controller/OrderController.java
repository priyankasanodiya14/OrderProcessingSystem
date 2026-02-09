package com.example.cashinvoice.controller;

import com.example.cashinvoice.model.Order;
import com.example.cashinvoice.model.OrderRequest;
import com.example.cashinvoice.model.OrderResponse;
import com.example.cashinvoice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private  final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
   // create order
   @PostMapping
    public ResponseEntity<OrderResponse>createOrder(
           @RequestBody OrderRequest request){
        OrderResponse response = orderService.createOrder(request);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   // get order by id
    @GetMapping("/{orderId}")
    public ResponseEntity<Order>getOrderById(
            @PathVariable String orderId){
        Order order = orderService.getOrderById(orderId);
        return  ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrderByCustomerId(
            @RequestParam String customerId){
        List<Order>orders = orderService.getOrderByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

}
