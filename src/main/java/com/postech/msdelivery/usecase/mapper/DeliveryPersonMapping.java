package com.postech.msdelivery.usecase.mapper;

import com.postech.msdelivery.dto.DeliveryPersonDTO;
import com.postech.msdelivery.entity.DeliveryPerson;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface DeliveryPersonMapping {

    DeliveryPersonMapping INSTANCE = Mappers.getMapper(DeliveryPersonMapping.class);

    DeliveryPersonDTO toDeliveryPersonDTO(DeliveryPerson delivery);

    DeliveryPerson toDeliveryPerson(DeliveryPersonDTO deliveryDTO);
}


