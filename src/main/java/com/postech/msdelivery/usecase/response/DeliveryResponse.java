package com.postech.msdelivery.usecase.response;

import com.postech.msdelivery.dto.DeliveryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryResponse {

    private final boolean success;
    private final DeliveryDTO delivery;
    private final String errorMessage;


    public DeliveryResponse(DeliveryDTO delivery) {
        this.delivery = delivery;
        this.success = true;
        this.errorMessage = null;
    }

    public DeliveryResponse(String message) {
        this.success = false;
        this.errorMessage = message;
        this.delivery = null;
    }

}



