package com.postech.msdelivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class DriverDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;
    @Valid
    @Email
    private String email;

}

