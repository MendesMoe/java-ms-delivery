package com.postech.msdelivery.usecase.mapper;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.dto.DeliveryPersonDTO;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryPerson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface DeliveryPersonMapping {

    DeliveryPersonDTO toDeliveryPersonDTO(DeliveryPerson delivery);

    DeliveryPerson toDeliveryPerson(DeliveryPersonDTO deliveryDTO);
}


