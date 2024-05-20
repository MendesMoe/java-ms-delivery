package com.postech.msdelivery.usecase.response;

import com.postech.msdelivery.dto.DriverDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryPersonResponse {

    private final boolean success;
    private final DriverDTO driverDTO;
    private final String errorMessage;


    public DeliveryPersonResponse(DriverDTO driverDTO) {
        this.driverDTO = driverDTO;
        this.success = true;
        this.errorMessage = null;
    }

    public DeliveryPersonResponse(String message) {
        this.success = false;
        this.errorMessage = message;
        this.driverDTO = null;
    }

}



