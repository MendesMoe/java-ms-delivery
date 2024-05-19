package com.postech.msdelivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DeliveryManDTO {
    private String id;
    @NotNull
    private String name;

}