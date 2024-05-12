package com.postech.msdelivery.usecase;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryUseCaseTest {
    @Test
    void testValidateInsertDelivery_Valido() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13","4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);
        assertEquals(DeliveryUseCase.validateSaveDelivery(delivery),true);
    }
    @Test
    void testValidateInsertDelivery_Invalido() {
        Delivery delivery = new Delivery();
        assertEquals(DeliveryUseCase.validateSaveDelivery(delivery),false);
    }

}
