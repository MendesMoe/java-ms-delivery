package com.postech.msdelivery.controller;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryMan;
import com.postech.msdelivery.gateway.DeliveryGateway;
import com.postech.msdelivery.gateway.DeliveryManGateway;
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
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryGateway deliveryGateway;
    private final DeliveryManGateway deliveryManGateway;

    @PostMapping("")
    @Operation(summary = "Request for create a Delivery", responses = {
            @ApiResponse(description = "The Delivery was updated", responseCode = "200")
    })

    public ResponseEntity<?> createDelivery(@Valid @RequestBody DeliveryDTO deliveryDTO) {
        log.info("PostMapping - createDelivery for customer [{}]", deliveryDTO.getIdOrder());
        try {
            Delivery deliveryNew = new Delivery(deliveryDTO);
            UUID idCustomer = deliveryGateway.getCustomerIdFromOrder(deliveryNew.getIdOrder());
            if (idCustomer != null && DeliveryUseCase.validateInsertDelivery(deliveryNew)) {
                Delivery deliveryCreated = deliveryGateway.createDelivery(deliveryNew);
                return new ResponseEntity<>(deliveryCreated, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Não foi possivel criar a entrega.", HttpStatus.BAD_REQUEST);
            }
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
            Delivery delivery = deliveryGateway.findDelivery(deliveryDTO.getId());
            Delivery deliveryNew = new Delivery(deliveryDTO);

            delivery.setIdDeliveryMan(deliveryNew.getIdDeliveryMan() != null ?
                    deliveryNew.getIdDeliveryMan() : delivery.getIdDeliveryMan());

            delivery.setStatus(deliveryNew.getStatus() != 0 ?
                    deliveryNew.getStatus() : delivery.getStatus());

            delivery.setDeliveryStartDate(deliveryNew.getDeliveryStartDate() != null ?
                    deliveryNew.getDeliveryStartDate() : delivery.getDeliveryStartDate());

            delivery.setExpectedDeliveryEndDate(deliveryNew.getExpectedDeliveryEndDate() != null ?
                    deliveryNew.getExpectedDeliveryEndDate() : delivery.getExpectedDeliveryEndDate());

            DeliveryMan DeliveryMan = deliveryManGateway.findDeliveryMan(String.valueOf(deliveryNew.getIdDeliveryMan()));
            deliveryNew.setIdDeliveryMan(DeliveryMan == null ? null : DeliveryMan.getId());

            UUID idCustomer = deliveryGateway.getCustomerIdFromOrder(deliveryNew.getIdOrder());
            if (idCustomer != null && DeliveryUseCase.validateInsertDelivery(delivery)) {
                Delivery deliveryCreated = deliveryGateway.createDelivery(delivery);
                return new ResponseEntity<>(deliveryCreated, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Não foi possivel atualizar a entrega.", HttpStatus.BAD_REQUEST);
            }
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
