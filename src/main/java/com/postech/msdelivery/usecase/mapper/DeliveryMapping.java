package com.postech.msdelivery.usecase.mapper;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeliveryMapping {

    DeliveryMapping INSTANCE = Mappers.getMapper(DeliveryMapping.class);

    @Mappings({
            @Mapping(source = "delivery.id", target = "deliveryDTO.id"),
            @Mapping(source = "delivery.status", target = "deliveryDTO.status"),
            @Mapping(source = "delivery.description", target = "deliveryDTO.description"),
    })
    DeliveryDTO toDeliveryDTO(Delivery delivery);

    @Mappings({
            @Mapping(source = "deliveryDTO.id", target = "delivery.id"),
            @Mapping(source = "deliveryDTO.status", target = "delivery.status"),
            @Mapping(source = "deliveryDTO.description", target = "delivery.description"),
    })
    Delivery toDelivery(DeliveryDTO deliveryDTO);
}

