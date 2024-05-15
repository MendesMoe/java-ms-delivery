package com.postech.msdelivery.usecase;

import com.postech.msdelivery.dto.DeliveryPersonDTO;
import com.postech.msdelivery.entity.DeliveryPerson;
import com.postech.msdelivery.repository.DeliveryPersonRepository;
import com.postech.msdelivery.usecase.mapper.DeliveryMapping;
import com.postech.msdelivery.usecase.mapper.DeliveryPersonMapping;
import com.postech.msdelivery.usecase.response.DeliveryPersonResponse;
import com.postech.msdelivery.usecase.response.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateDeliveryPersonUseCase {

    private final DeliveryPersonRepository deliveryPersonRepository;
    private final DeliveryPersonMapping deliveryPersonMapping;


    public DeliveryPersonResponse execute(DeliveryPersonDTO deliveryPersonDTO) {
        DeliveryPerson deliveryPerson = new DeliveryPerson(deliveryPersonDTO.getName(), deliveryPersonDTO.getEmail());
        deliveryPerson.setAvailable(true);
        DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        deliveryPersonDTO = deliveryPersonMapping.toDeliveryPersonDTO(savedDeliveryPerson);
        return new DeliveryPersonResponse(deliveryPersonDTO);
    }

}

