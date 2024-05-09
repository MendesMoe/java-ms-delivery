package com.postech.msdelivery.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class DeliveryDTO {
    private String id;
    @NotNull
    private String idOrder;
    private String idDeliveryMan;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String deliveryStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String expectedDeliveryEndDate;
    private int status;
    public DeliveryDTO(String idOrder) {
        this.idOrder = idOrder;
        this.idDeliveryMan = null;
        this.deliveryStartDate = "2000-01-01 00:00:00";
        this.expectedDeliveryEndDate = "2000-01-01 00:00:00";
        this.status = 0;
    }
}