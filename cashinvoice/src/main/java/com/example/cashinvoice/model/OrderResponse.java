package com.example.cashinvoice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {

    private  String orderId;
    private  String status;

}
