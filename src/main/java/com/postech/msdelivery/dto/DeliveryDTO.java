package com.postech.msdelivery.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.postech.msdelivery.entity.DeliveryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DeliveryDTO {
    private String id;
    @NotNull
    private String orderUuid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String deliveryStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String estimatedDeliveryTime;
    private String status;
    private Object deliveryPersonDTO;

    public DeliveryDTO(String orderUuid) {
        this.orderUuid = orderUuid;
        this.deliveryPersonDTO = new Object();
        this.deliveryStartDate = "2000-01-01 00:00:00";
        this.estimatedDeliveryTime = "2000-01-01 00:00:00";
        this.status = DeliveryStatus.PLACED.name();
    }


}