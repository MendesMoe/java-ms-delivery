package com.postech.msdelivery.controller;

import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.exception.ResourceNotFoundException;
import com.postech.msdelivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryControllerV2 {

   @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestParam Long orderId) {
        Delivery delivery = deliveryService.createDelivery(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        try {
            Delivery delivery = deliveryService.getDeliveryById(id);
            return ResponseEntity.ok(delivery);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

