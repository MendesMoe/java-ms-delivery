package com.postech.msdelivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class DeliveryPersonDTO {

    @Valid
    private String name;
    @Valid
    @Email
    private String email;

}

