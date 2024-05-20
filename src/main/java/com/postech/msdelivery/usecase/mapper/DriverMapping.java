package com.postech.msdelivery.usecase.mapper;

import com.postech.msdelivery.dto.DriverDTO;
import com.postech.msdelivery.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface DriverMapping {

    DriverMapping INSTANCE = Mappers.getMapper(DriverMapping.class);

    DriverDTO toDriverDTO(Driver delivery);

    Driver toDriver(DriverDTO deliveryDTO);
}


