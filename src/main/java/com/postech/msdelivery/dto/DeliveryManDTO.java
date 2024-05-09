package com.postech.msdelivery.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryManDTO {
    private String id;
    @NotNull
    private String name;

}