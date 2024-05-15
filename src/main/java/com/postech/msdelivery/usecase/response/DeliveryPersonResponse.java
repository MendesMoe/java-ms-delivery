package com.postech.msdelivery.usecase.response;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.dto.DeliveryPersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryPersonResponse {

    private final boolean success;
    private final DeliveryPersonDTO deliveryPersonDTO;
    private final String errorMessage;


    public DeliveryPersonResponse(DeliveryPersonDTO deliveryPersonDTO) {
        this.deliveryPersonDTO = deliveryPersonDTO;
        this.success = true;
        this.errorMessage = null;
    }

    public DeliveryPersonResponse(String message) {
        this.success = false;
        this.errorMessage = message;
        this.deliveryPersonDTO = null;
    }

}



