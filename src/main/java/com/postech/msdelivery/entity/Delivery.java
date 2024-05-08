package com.postech.msdelivery.entity;

import com.postech.msdelivery.dto.DeliveryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String deliveryManName;
    private int status;
    @Transient
    private String statusDescription;
    private LocalDateTime deliveryStartDate;
    private LocalDateTime expectedDeliveryEndDate;

    public String getStatusDescription() {
        switch (this.status) {
            case 0:
                return "Aguardando Envio";
            case 1:
                return "Em rota de envio";
            case 2:
                return "A caminho";
            case 3:
                return "Entregue";
            case 4:
                return "Entrega Rejeitada";
            case 5:
                return "Entrega Cancelada";
        }
        return statusDescription;
    }

    public Delivery(DeliveryDTO deliveryDTO) {
        this.id = UUID.randomUUID();
        this.idOrder = UUID.fromString(deliveryDTO.getIdOrder());
        this.deliveryManName = deliveryDTO.getDeliveryManName();
        this.status = deliveryDTO.getStatus();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.deliveryStartDate = LocalDateTime.parse(deliveryDTO.getDeliveryStartDate(), formatter);
        this.expectedDeliveryEndDate = LocalDateTime.parse(deliveryDTO.getExpectedDeliveryEndDate(), formatter);
    }
}
