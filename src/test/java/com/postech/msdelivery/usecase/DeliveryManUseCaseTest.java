package com.postech.msdelivery.usecase;

import com.postech.msdelivery.entity.DeliveryMan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class DeliveryManUseCaseTest {
    @Test
    void testValidarInsertDeliveryMan_ValidInput() {
        // Arrange
        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setName("John Doe");

        // Act & Assert
        assertDoesNotThrow(() -> DeliveryManUseCase.validarInsertDeliveryMan(deliveryMan));
    }

    @Test
    void testValidarInsertDeliveryMan_NullName_ThrowsException() {
        // Arrange
        DeliveryMan deliveryMan = new DeliveryMan();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> DeliveryManUseCase.validarInsertDeliveryMan(deliveryMan));
    }

    @Test
    void testValidarUpdateEntregador_ValidInput() {
        // Arrange
        String deliveryManId = UUID.randomUUID().toString();
        DeliveryMan deliveryManToUpdate = new DeliveryMan();
        deliveryManToUpdate.setId(UUID.fromString(deliveryManId));
        DeliveryMan deliveryManNew = new DeliveryMan();
        deliveryManNew.setId(UUID.fromString(deliveryManId));

        // Act & Assert
        assertDoesNotThrow(() -> DeliveryManUseCase.validarUpdateEntregador(deliveryManId, deliveryManToUpdate, deliveryManNew));
    }

    @Test
    void testValidarUpdateEntregador_DeliveryManNotFound_ThrowsException() {
        // Arrange
        String deliveryManId = UUID.randomUUID().toString();
        DeliveryMan deliveryManNew = new DeliveryMan();
        deliveryManNew.setId(UUID.fromString(deliveryManId));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> DeliveryManUseCase.validarUpdateEntregador(deliveryManId, null, deliveryManNew));
    }

    @Test
    void testValidarDeleteEntregador_ValidInput() {
        // Arrange
        DeliveryMan deliveryMan = new DeliveryMan();

        // Act & Assert
        assertDoesNotThrow(() -> DeliveryManUseCase.validarDeleteEntregador(deliveryMan));
    }

    @Test
    void testValidarDeleteEntregador_DeliveryManNotFound_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> DeliveryManUseCase.validarDeleteEntregador(null));
    }
}
