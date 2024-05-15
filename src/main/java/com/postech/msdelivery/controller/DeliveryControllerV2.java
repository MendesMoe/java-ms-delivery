package com.postech.msdelivery.controller;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.usecase.CreateDeliveryUseCase;
import com.postech.msdelivery.usecase.response.DeliveryResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryControllerV2 {

    private final CreateDeliveryUseCase createDeliveryUseCase;
    /*private final GetDeliveryUseCase getDeliveryUseCase;
    private final UpdateDeliveryUseCase updateDeliveryUseCase;
    private final DeliveryMapper deliveryMapper;*/


    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        //CreateDeliveryRequest request = deliveryMapper.toCreateDeliveryRequest(deliveryDTO);
        DeliveryResponse response = createDeliveryUseCase.execute(deliveryDTO);
        return buildDeliveryResponse(response);
    }

/*
    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> getDelivery(@PathVariable Long deliveryId) {
       // GetDeliveryRequest request = new GetDeliveryRequest(deliveryId);
        DeliveryResponse response = getDeliveryUseCase.execute(request);
        return buildDeliveryResponse(response);
    }

    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> updateDelivery(@PathVariable Long deliveryId, @RequestBody DeliveryDTO deliveryDTO) {
        //UpdateDeliveryRequest request = deliveryMapper.toUpdateDeliveryRequest(deliveryId, deliveryDTO);
        DeliveryResponse response = updateDeliveryUseCase.execute(request);
        return buildDeliveryResponse(response);
    }*/

    private ResponseEntity<DeliveryResponse> buildDeliveryResponse(DeliveryResponse response) {
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

