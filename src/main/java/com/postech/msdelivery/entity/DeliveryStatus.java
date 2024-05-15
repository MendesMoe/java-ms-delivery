package com.postech.msdelivery.entity;

public enum DeliveryStatus {

    PLACED, // Order placed, awaiting assignment to a delivery person
    ASSIGNED, // Order assigned to a delivery person
    IN_PROGRESS, // Delivery person is on the way to pick up the order
    PICKED_UP, // Delivery person has picked up the order
    OUT_FOR_DELIVERY, // Delivery person is on their way to deliver the order
    DELIVERED, // Order has been successfully delivered to the customer
    CANCELED, // Order has been canceled
    FAILED // Delivery failed due to unforeseen circumstances
}
