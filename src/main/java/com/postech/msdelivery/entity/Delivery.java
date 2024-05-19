package com.postech.msdelivery.entity;

import com.postech.msdelivery.dto.DeliveryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "tb_delivery")
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column
    private LocalDateTime estimatedDeliveryTime;

    @Column
    private String customerAddress;

    @Column(nullable = false)
    private BigDecimal currentLatitude;

    @Column(nullable = false)
    private BigDecimal currentLongitude;

    //@OneToMany(mappedBy = "delivery")
    //private List<DeliveryTracking> tracking;

    public String getStatusDescription() {
        switch (this.status) {
            case PLACED:
                return "Aguardando Envio";
            case IN_PROGRESS:
                return "Em rota de envio";
            case OUT_FOR_DELIVERY:
                return "A caminho";
            case DELIVERED:
                return "Entregue";
            case FAILED:
                return "Entrega Rejeitada";
            case CANCELED:
                return "Entrega Cancelada";
        }
        return null;
    }

    public Delivery(DeliveryDTO deliveryDTO) {
        this.orderId = deliveryDTO.getOrderId();
        //this.deliveryPerson = deliveryDTO.getDeliveryPersonDTO();
        this.deliveryPerson = null;
        this.status = DeliveryStatus.valueOf(deliveryDTO.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.startDate = LocalDateTime.parse(deliveryDTO.getDeliveryStartDate(), formatter);
        this.estimatedDeliveryTime = LocalDateTime.parse(deliveryDTO.getEstimatedDeliveryTime(), formatter);
    }
}
