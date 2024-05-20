package com.postech.msdelivery.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.gateway.DeliveryGateway;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_Delivery")
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID idOrder;
    private UUID idDeliveryMan;
    private int status;
    @Transient
    private String statusDescription;
    @Transient
    private String cepCustomer;
    private LocalDateTime deliveryStartDate;
    private LocalDateTime expectedDeliveryEndDate;

    public String getStatusDescription() {
        return switch (this.status) {
            case 0 -> "Aguardando Envio";
            case 1 -> "Em rota de envio";
            case 2 -> "A caminho";
            case 3 -> "Entregue";
            case 4 -> "Entrega Rejeitada";
            case 5 -> "Entrega Cancelada";
            default -> "Status Inválido";
        };
    }

    public String getCepCustomer() {
        try {
            return DeliveryGateway.getCepCustomer(DeliveryGateway.getCustomerIdFromOrder(idOrder));
        } catch (Exception e) {
            return "";
        }
    }

    public Delivery(DeliveryDTO deliveryDTO) {
        this.id = deliveryDTO.getId() != null ? UUID.fromString(deliveryDTO.getId()) : UUID.randomUUID();
        this.idOrder = UUID.fromString(deliveryDTO.getIdOrder());
        this.idDeliveryMan = UUID.fromString(deliveryDTO.getIdDeliveryMan());
        this.status = deliveryDTO.getStatus();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.deliveryStartDate = LocalDateTime.parse(deliveryDTO.getDeliveryStartDate(), formatter);
        this.expectedDeliveryEndDate = LocalDateTime.parse(deliveryDTO.getExpectedDeliveryEndDate(), formatter);
    }
}
