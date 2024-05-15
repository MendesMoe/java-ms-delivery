package com.postech.msdelivery.controller;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.gateway.DeliveryGateway;
import com.postech.msdelivery.usecase.DeliveryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryGateway deliveryGateway;

    @PostMapping("")
    @Operation(summary = "Request for create a Delivery", responses = {
            @ApiResponse(description = "The Delivery was updated", responseCode = "200")
    })

    public ResponseEntity<?> createDelivery(@Valid @RequestBody DeliveryDTO deliveryDTO) {
        log.info("PostMapping - createDelivery for customer [{}]", deliveryDTO.getOrderId());
        try {
            Delivery deliveryNew = new Delivery(deliveryDTO);
            DeliveryUseCase.validateInsertDelivery(deliveryNew);
            Delivery deliveryCreated = deliveryGateway.createDelivery(deliveryNew);
            return new ResponseEntity<>(deliveryCreated, HttpStatus.CREATED);
        } catch (HttpClientErrorException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("")
    @Operation(summary = "Request for update a Delivery", responses = {
            @ApiResponse(description = "The Delivery was updated", responseCode = "200")
    })
    public ResponseEntity<?> updateDelivery(@RequestBody @Valid DeliveryDTO deliveryDTO) {
        log.info("update Delivery [{}]", deliveryDTO.getId());

        try {
            Delivery delivery = deliveryGateway.findDelivery(Long.valueOf(deliveryDTO.getId()));
            Delivery deliveryNew = new Delivery(deliveryDTO);

           /* delivery.setDeliveryManName(deliveryNew.getDeliveryManName() != null ?
                    deliveryNew.getDeliveryManName() : delivery.getDeliveryManName());

            delivery.setStatus(deliveryNew.getStatus() != 0 ?
                    deliveryNew.getStatus() : delivery.getStatus());

            delivery.setDeliveryStartDate(deliveryNew.getDeliveryStartDate() != null ?
                    deliveryNew.getDeliveryStartDate() : delivery.getDeliveryStartDate());

            delivery.setExpectedDeliveryEndDate(deliveryNew.getExpectedDeliveryEndDate() != null ?
                    deliveryNew.getExpectedDeliveryEndDate() : delivery.getExpectedDeliveryEndDate());*/

            DeliveryUseCase.validateInsertDelivery(delivery);
            Delivery deliveryCreated = deliveryGateway.createDelivery(delivery);
            return new ResponseEntity<>(deliveryCreated, HttpStatus.CREATED);
        } catch (HttpClientErrorException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping
    @Operation(summary = "Request for list all delivery", responses = {
            @ApiResponse(description = "Delivery's list", responseCode = "200"),
    })
    public ResponseEntity<List<Delivery>> listAllDeliverys() {
        log.info("GetAllDeliverys");
        List<Delivery> delivery = deliveryGateway.listAllDeliverys();
        return new ResponseEntity<>(delivery, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get only Delivery by ID", responses = {
            @ApiResponse(description = "The delivery by ID", responseCode = "200", content = @Content(schema = @Schema(implementation = Delivery.class))),
            @ApiResponse(description = "Delivery Not Found", responseCode = "404", content = @Content(schema = @Schema(type = "string", example = "Entrega não encontrado.")))
    })
    public ResponseEntity<?> findDelivery(@PathVariable Long id) {
        log.info("GetMapping - FindDelivery");
        Delivery delivery = deliveryGateway.findDelivery(id);
        if (delivery != null) {
            return new ResponseEntity<>(delivery, HttpStatus.OK);
        }
        return new ResponseEntity<>("Entrega não encontrado.", HttpStatus.NOT_FOUND);
    }
}
