package com.postech.msdelivery.usecase;

import com.postech.msdelivery.entity.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeliveryUseCaseTest {
    @Test
    void testValidarInsertDelivery_ValidInput() {
//        // Arrange
//        Delivery delivery = new Delivery();
//        when(DeliveryUseCase.validateInsertDelivery(delivery).thenReturn(delivery);
//
//        // Act & Assert
//        assertDoesNotThrow(() -> DeliveryUseCase.validateInsertDelivery(delivery));
    }

    @Test
    void testValidateInsertDelivery_ClientNotFound_ThrowsException() {
        Delivery delivery = new Delivery();
        assertThrows(HttpClientErrorException.class, () -> DeliveryUseCase.validateInsertDelivery(delivery));
    }

}
