package com.postech.msdelivery.controller;

import com.postech.msdelivery.dto.DeliveryPersonDTO;
import com.postech.msdelivery.usecase.CreateDeliveryPersonUseCase;
import com.postech.msdelivery.usecase.response.DeliveryPersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-persons")
public class DeliveryPersonController {

    private final CreateDeliveryPersonUseCase createDeliveryPersonUseCase;

    @PostMapping
    public ResponseEntity<DeliveryPersonResponse> createDeliveryPerson(@RequestBody @Valid DeliveryPersonDTO deliveryPersonDTO) {
        DeliveryPersonResponse deliveryPersonResponse = createDeliveryPersonUseCase.execute(deliveryPersonDTO);
        return buildDeliveryPersonResponse(deliveryPersonResponse);
    }

    private ResponseEntity<DeliveryPersonResponse> buildDeliveryPersonResponse(DeliveryPersonResponse response) {
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
