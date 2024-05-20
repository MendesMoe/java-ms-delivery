package com.postech.msdelivery.usecase;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryUseCaseTest {
    @Test
    void testValidateInsertDelivery_Valido() {
        DeliveryDTO deliveryDTO = new DeliveryDTO(UUID.randomUUID().toString());
        Delivery delivery = new Delivery(deliveryDTO);
        assertEquals(DeliveryUseCase.validateSaveDelivery(delivery),true);
    }
    @Test
    void testValidateInsertDelivery_Invalido() {
        Delivery delivery = new Delivery();
        assertEquals(DeliveryUseCase.validateSaveDelivery(delivery),false);
    }

}
