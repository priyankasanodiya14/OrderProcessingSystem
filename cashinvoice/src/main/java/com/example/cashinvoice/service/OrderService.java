package com.example.cashinvoice.service;

import com.example.cashinvoice.exception.InvalidOrderException;
import com.example.cashinvoice.exception.OrderNotFoundException;
import com.example.cashinvoice.model.Order;
import com.example.cashinvoice.model.OrderRequest;
import com.example.cashinvoice.model.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {

    @Autowired
    private ObjectMapper objectMapper;
    private  static  final Logger log = LoggerFactory.getLogger(OrderService.class);
    // for in memory storage
    private  final Map<String, Order> orderStore = new ConcurrentHashMap<>();

    // Creating order
    public OrderResponse createOrder(OrderRequest request){

        // validation
        if (request.getCustomerId() == null || request.getCustomerId().isEmpty()){
            throw new InvalidOrderException("CustomerId must not be null or empty");
        }

        if (request.getProduct() == null || request.getProduct().isEmpty()){
            throw new InvalidOrderException("Product must not be null or empty");
        }
        if(request.getAmount() <= 0){
            throw new InvalidOrderException("Amount must be greater than 0");
        }

        // generate orderId

        String orderId = UUID.randomUUID().toString();

        //generate createdAt

        LocalDateTime createdAt = LocalDateTime.now();

        // Create order object

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(request.getCustomerId());
        order.setProduct(request.getProduct());
        order.setAmount(request.getAmount());
        order.setCreatedAt(createdAt);

        // In memory storage
        orderStore.put(orderId,order);

        // writing order to file system
        writeOrderToFile(order);

        return  new OrderResponse(orderId, "CREATED");



    }


    // get order by id
    public  Order getOrderById(String orderId){
        Order order = orderStore.get(orderId);

        if(order == null){
            throw  new OrderNotFoundException("Order not found with id: " + orderId);
        }
        return  order;
    }

    // get order by customer id

    public List<Order> getOrderByCustomerId(String customerId){
        List<Order> orders = new ArrayList<>();

        for(Order order : orderStore.values()){
            if (order.getCustomerId().equals(customerId)){
                orders.add(order);
            }
        }
        return orders;
    }

    // writing order JSON to file logic

    private void writeOrderToFile(Order order){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            Path directory = Paths.get("input/orders");
            Files.createDirectories(directory);

            Path filePath = directory.resolve("order-" + order.getOrderId() + ".json");
            mapper.writeValue(filePath.toFile(),order);
            log.info("Order file written successfully:{}", filePath.toAbsolutePath()
            );

        } catch (Exception e){
            log.error("Failed to write order file for order id ={}", order.getOrderId(),e);
        }
    }
}
