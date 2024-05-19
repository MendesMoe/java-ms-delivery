package com.postech.msdelivery.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Order {
    private Long id;
    private String customerAddress;
    // ... other fields (e.g., order items, total price, etc.)




}

