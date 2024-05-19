package com.postech.msdelivery.usecase;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryStatus;
import com.postech.msdelivery.exception.DeliveryAlreadyExistsException;
import com.postech.msdelivery.service.DeliveryService;
import com.postech.msdelivery.usecase.mapper.DeliveryMapping;
import com.postech.msdelivery.usecase.response.DeliveryResponse;
import org.springframework.stereotype.Component;

@Component
public class CreateDeliveryUseCase {

    private final DeliveryService deliveryService;

    public CreateDeliveryUseCase(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public DeliveryResponse execute(DeliveryDTO request) {
        //TODO criar evento ao criar novo objeto? notificar?
        Delivery delivery = new Delivery();
        delivery.setOrderId(request.getOrderId());
        delivery.setDeliveryPerson(null);
        delivery.setStatus(DeliveryStatus.PLACED);

        try {
            deliveryService.createDelivery(delivery);

            return new DeliveryResponse(DeliveryMapping.INSTANCE.toDeliveryDTO(delivery));
        } catch (DeliveryAlreadyExistsException e) {
            return new DeliveryResponse(e.getMessage());
        } catch (Exception e) {
            return new DeliveryResponse("An error occurred while creating delivery: " + e.getMessage());
        }
    }
}

