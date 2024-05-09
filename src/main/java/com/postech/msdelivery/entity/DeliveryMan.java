package com.postech.msdelivery.entity;

import com.postech.msdelivery.dto.DeliveryManDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_DeliveryMan")
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    public DeliveryMan(DeliveryManDTO deliveryManDTO) {
        this.id = UUID.randomUUID();
        this.name = deliveryManDTO.getName();
    }
}
