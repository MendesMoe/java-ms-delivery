package com.postech.msdelivery.controller;
import com.postech.msdelivery.dto.DeliveryManDTO;
import com.postech.msdelivery.entity.DeliveryMan;
import com.postech.msdelivery.gateway.DeliveryManGateway;
import com.postech.msdelivery.usecase.DeliveryManUseCase;
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

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/deliveryman")
@RequiredArgsConstructor
public class DeliveryManController {

    private final DeliveryManGateway deliveryManGateway;

    @PostMapping("")
    @Operation(summary = "Request for create a DeliveryMan", responses = {
            @ApiResponse(description = "The new DeliveryMans was created", responseCode = "201", content = @Content(schema = @Schema(implementation = DeliveryMan.class))),
            @ApiResponse(description = "Fields Invalid", responseCode = "400", content = @Content(schema = @Schema(type = "string", example = "Campos inválidos ou faltando: cep, estado, cidade, endereco")))
    })
    public ResponseEntity<?> createDeliveryMan(@Valid @RequestBody DeliveryManDTO deliveryManDTO) {
        log.info("PostMapping - createDeliveryMan for DeliveryMan [{}]", deliveryManDTO.getName());
        try {
            DeliveryMan deliveryManNew = new DeliveryMan(deliveryManDTO);
            DeliveryManUseCase.validarInsertDeliveryMan(deliveryManNew);
            DeliveryMan DeliveryManCreated = deliveryManGateway.createDeliveryMan(deliveryManNew);
            return new ResponseEntity<>(DeliveryManCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("")
    @Operation(summary = "Request for list all DeliveryMans", responses = {
            @ApiResponse(description = "DeliveryMan's list", responseCode = "200"),
    })
    public ResponseEntity<List<DeliveryMan>> listAllDeliveryMans() {
        log.info("GetMapping - listDeliveryMans");
        List<DeliveryMan> DeliveryMans = deliveryManGateway.listAllDeliveryMans();
        return new ResponseEntity<>(DeliveryMans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get only DeliveryMan by ID", responses = {
            @ApiResponse(description = "The DeliveryMan by ID", responseCode = "200", content = @Content(schema = @Schema(implementation = DeliveryMan.class))),
            @ApiResponse(description = "DeliveryMan Not Found", responseCode = "404", content = @Content(schema = @Schema(type = "string", example = "Entregador não encontrado.")))
    })
    public ResponseEntity<?> findDeliveryMan(@PathVariable String id) {
        log.info("GetMapping - FindDeliveryMan");
        DeliveryMan DeliveryMan = deliveryManGateway.findDeliveryMan(id);
        if (DeliveryMan != null) {
            return new ResponseEntity<>( DeliveryMan , HttpStatus.OK);
        }
        return new ResponseEntity<>("Entregador não encontrado.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Request for update a DeliveryMan by ID", responses = {
            @ApiResponse(description = "The DeliveryMans was updated", responseCode = "200", content = @Content(schema = @Schema(implementation = DeliveryMan.class)))
    })
    public ResponseEntity<?> updateDeliveryMan(@PathVariable String id, @RequestBody @Valid DeliveryManDTO DeliveryManDTO) {
        log.info("PutMapping - updateDeliveryMan");
        try {
            DeliveryMan DeliveryManOld = deliveryManGateway.findDeliveryMan(id);
            DeliveryMan DeliveryManNew = new DeliveryMan(DeliveryManDTO);
            DeliveryManNew.setId(UUID.fromString(id));
            DeliveryManUseCase.validarUpdateEntregador(id, DeliveryManOld, DeliveryManNew);
            DeliveryManNew = deliveryManGateway.updateDeliveryMan(DeliveryManNew);
            return new ResponseEntity<>(DeliveryManNew, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a DeliveryMan by ID", responses = {
            @ApiResponse(description = "The DeliveryMan was deleted", responseCode = "200", content = @Content(schema = @Schema(type = "string", example = "Entregador removido."))),
            @ApiResponse(description = "DeliveryMan Not Found", responseCode = "404", content = @Content(schema = @Schema(type = "string", example = "Entregador não encontrado.")))
    })
    public ResponseEntity<?> deleteDeliveryMan(@PathVariable String id) {
        log.info("DeleteMapping - deleteDeliveryMan");
        try {
            DeliveryMan DeliveryMan = deliveryManGateway.findDeliveryMan(id);
            DeliveryManUseCase.validarDeleteEntregador(DeliveryMan);
            deliveryManGateway.deleteDeliveryMan(id);
            return new ResponseEntity<>("Entregador removido.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

