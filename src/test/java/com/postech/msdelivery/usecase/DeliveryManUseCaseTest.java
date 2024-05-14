package com.postech.msdelivery.usecase;

import com.postech.msdelivery.entity.DeliveryMan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class DeliveryManUseCaseTest {
    @Test
    void testValidarInsertDeliveryMan_ValidInput() {
        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setName("John Doe");
        assertDoesNotThrow(() -> DeliveryManUseCase.validarInsertDeliveryMan(deliveryMan));
    }

    @Test
    void testValidarInsertDeliveryMan_NullName_ThrowsException() {
        DeliveryMan deliveryMan = new DeliveryMan();
        assertThrows(IllegalArgumentException.class, () -> DeliveryManUseCase.validarInsertDeliveryMan(deliveryMan));
    }

    @Test
    void testValidarUpdateEntregador_ValidInput() {
        String deliveryManId = UUID.randomUUID().toString();
        DeliveryMan deliveryManToUpdate = new DeliveryMan();
        deliveryManToUpdate.setId(UUID.fromString(deliveryManId));
        DeliveryMan deliveryManNew = new DeliveryMan();
        deliveryManNew.setId(UUID.fromString(deliveryManId));
        assertDoesNotThrow(() -> DeliveryManUseCase.validarUpdateEntregador(deliveryManId, deliveryManToUpdate, deliveryManNew));
    }

    @Test
    void testValidarUpdateEntregador_DeliveryManNotFound_ThrowsException() {
        // Arrange
        String deliveryManId = UUID.randomUUID().toString();
        DeliveryMan deliveryManNew = new DeliveryMan();
        deliveryManNew.setId(UUID.fromString(deliveryManId));
        assertThrows(IllegalArgumentException.class, () -> DeliveryManUseCase.validarUpdateEntregador(deliveryManId, null, deliveryManNew));
    }

    @Test
    void testValidarDeleteEntregador_ValidInput() {
        DeliveryMan deliveryMan = new DeliveryMan();
        assertDoesNotThrow(() -> DeliveryManUseCase.validarDeleteEntregador(deliveryMan));
    }

    @Test
    void testValidarDeleteEntregador_DeliveryManNotFound_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> DeliveryManUseCase.validarDeleteEntregador(null));
    }
}
