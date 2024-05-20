package com.postech.msdelivery.usecase;

import com.postech.msdelivery.entity.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Component
@Service
public class DeliveryUseCase {
    public static boolean validateSaveDelivery(Delivery deliveryNew) {
        return (deliveryNew != null
                && deliveryNew.getOrderUuid() != null
                && deliveryNew.getDeliveryPerson() != null);
    }
}
