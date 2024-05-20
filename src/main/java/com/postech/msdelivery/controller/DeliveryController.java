package com.postech.msdelivery.controller;

import com.google.maps.errors.ApiException;
import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryMan;
import com.postech.msdelivery.gateway.DeliveryGateway;
import com.postech.msdelivery.gateway.DeliveryManGateway;
import com.postech.msdelivery.usecase.DeliveryUseCase;
import com.postech.msdelivery.usecase.DirectionsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryGateway deliveryGateway;
    private final DeliveryManGateway deliveryManGateway;
    private final DirectionsUseCase directionsUseCase;

    @PostMapping("")
    @Operation(summary = "Request for create a Delivery", responses = {
            @ApiResponse(description = "The Delivery was updated", responseCode = "200")
    })
    public ResponseEntity<?> createDelivery(@Valid @RequestBody DeliveryDTO deliveryDTO) {
        log.info("PostMapping - createDelivery [{}]", deliveryDTO.getIdOrder());
        try {
            Delivery deliveryNew = new Delivery(deliveryDTO);
            return saveNewDelivery(deliveryNew);
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
            Delivery deliveryToSave = deliveryGateway.findDelivery(deliveryDTO.getId());
            Delivery deliveryRequest = new Delivery(deliveryDTO);

            deliveryToSave.setIdDeliveryMan(deliveryRequest.getIdDeliveryMan() != null ?
                    deliveryRequest.getIdDeliveryMan() : deliveryToSave.getIdDeliveryMan());

            deliveryToSave.setStatus(deliveryRequest.getStatus() != 0 ?
                    deliveryRequest.getStatus() : deliveryToSave.getStatus());

            deliveryToSave.setDeliveryStartDate(deliveryRequest.getDeliveryStartDate() != null ?
                    deliveryRequest.getDeliveryStartDate() : deliveryToSave.getDeliveryStartDate());

            deliveryToSave.setExpectedDeliveryEndDate(deliveryRequest.getExpectedDeliveryEndDate() != null ?
                    deliveryRequest.getExpectedDeliveryEndDate() : deliveryToSave.getExpectedDeliveryEndDate());

            return saveNewDelivery(deliveryToSave);
        } catch (HttpClientErrorException enf) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enf.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @NotNull
    private ResponseEntity<?> saveNewDelivery(Delivery deliveryToSave) throws IOException, InterruptedException, ApiException {
        DeliveryMan DeliveryMan = deliveryManGateway.findDeliveryMan(deliveryToSave.getIdDeliveryMan().toString());
        deliveryToSave.setIdDeliveryMan(DeliveryMan == null ? null : deliveryToSave.getIdDeliveryMan());

        UUID idCustomer = deliveryGateway.getCustomerId(deliveryToSave.getIdOrder());
        deliveryToSave.setIdOrder(idCustomer == null ? null : deliveryToSave.getIdOrder());

        if (DeliveryUseCase.validateSaveDelivery(deliveryToSave)) {

            long durationInSec = directionsUseCase.calculateRoute(deliveryToSave.getCepCustomer());
            deliveryToSave.setExpectedDeliveryEndDate(LocalDateTime.now().plusSeconds(durationInSec));

            Delivery deliveryCreated = deliveryGateway.createDelivery(deliveryToSave);
            return new ResponseEntity<>(deliveryCreated, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Não foi possivel criar a entrega"
                    + (deliveryToSave.getIdOrder() == null ? " [Pedido ou Cliente inválido]" : "")
                    + (deliveryToSave.getIdDeliveryMan() == null ? " [Entregador inválido]" : "")
                    + "."
                    , HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> findDelivery(@PathVariable String id) {
        log.info("GetMapping - FindDelivery");
        Delivery delivery = deliveryGateway.findDelivery(id);
        if (delivery != null) {
            return new ResponseEntity<>(delivery, HttpStatus.OK);
        }
        return new ResponseEntity<>("Entrega não encontrado.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/best_route/{idDeliveryMan}")
    @Operation(summary = "Request for list all delivery by best route", responses = {
            @ApiResponse(description = "Delivery's list", responseCode = "200"),
    })
    public ResponseEntity<List<Delivery>> listAllDeliverysBestRoute(@PathVariable String idDeliveryMan) {
        log.info("Get Best Route");
        List<Delivery> delivery = deliveryGateway.findDeliverysByIdDeliveryMan(idDeliveryMan);
        return new ResponseEntity<>(delivery, HttpStatus.OK);
    }
}
