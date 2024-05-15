package com.postech.msdelivery.exception;

public class DeliveryAlreadyExistsException extends RuntimeException {

    public DeliveryAlreadyExistsException(String message) {
        super(message);
    }
}

