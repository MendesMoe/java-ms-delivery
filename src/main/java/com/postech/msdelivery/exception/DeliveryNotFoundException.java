package com.postech.msdelivery.exception;

public class DeliveryNotFoundException extends RuntimeException {

    private final Long deliveryId;

    public DeliveryNotFoundException(Long deliveryId) {
        super("Delivery with ID " + deliveryId + " not found");
        this.deliveryId = deliveryId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }
}

