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
    private Long orderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String deliveryStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String expectedDeliveryEndDate;
    private String status;
    private Object deliveryPersonDTO;

    public DeliveryDTO(Long orderId) {
        this.orderId = orderId;
        this.deliveryPersonDTO = new Object();
        this.deliveryStartDate = "2000-01-01 00:00:00";
        this.expectedDeliveryEndDate = "2000-01-01 00:00:00";
        this.status = DeliveryStatus.ASSIGNED.name();
    }


}