package com.example.cashinvoice.model;

import lombok.Data;

@Data

public class OrderRequest {
    private  String customerId;
    private  String product;
    private  double amount;
}
