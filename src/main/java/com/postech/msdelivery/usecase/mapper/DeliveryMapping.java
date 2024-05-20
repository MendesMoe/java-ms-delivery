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

    DeliveryDTO toDeliveryDTO(Delivery delivery);

    Delivery toDelivery(DeliveryDTO deliveryDTO);
}

