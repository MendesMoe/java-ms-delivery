package com.postech.msdelivery.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Order {
    private UUID id;

    private UUID idCustomer;

    private LocalDateTime orderDate;
    //private String customerAddress;
}

