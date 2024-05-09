package com.postech.msdelivery.gateway;

import com.postech.msdelivery.entity.DeliveryMan;
import com.postech.msdelivery.repository.DeliveryManRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DeliveryManGatewayTest {
    @InjectMocks
    private DeliveryManGateway deliveryManGateway;

    @Mock
    private DeliveryManRepository deliveryManRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDeliveryMan_ValidInput_ReturnsDeliveryMan() {
        // Arrange
        DeliveryMan deliveryMan = new DeliveryMan();
        // Mock the behavior of deliveryManRepository
        when(deliveryManRepository.save(deliveryMan)).thenReturn(deliveryMan);

        // Act
        DeliveryMan result = deliveryManGateway.createDeliveryMan(deliveryMan);

        // Assert
        assertEquals(deliveryMan, result);
    }

    @Test
    void testUpdateDeliveryMan_ValidInput_ReturnsDeliveryMan() {
        // Arrange
        DeliveryMan deliveryMan = new DeliveryMan();
        // Mock the behavior of deliveryManRepository
        when(deliveryManRepository.save(deliveryMan)).thenReturn(deliveryMan);

        // Act
        DeliveryMan result = deliveryManGateway.updateDeliveryMan(deliveryMan);

        // Assert
        assertEquals(deliveryMan, result);
    }

    @Test
    void testDeleteDeliveryMan_ValidInput_ReturnsTrue() {
        // Arrange
        UUID deliveryManId = UUID.randomUUID();
        // Mock the behavior of deliveryManRepository
        doNothing().when(deliveryManRepository).deleteById(deliveryManId);

        // Act
        boolean result = deliveryManGateway.deleteDeliveryMan(String.valueOf(deliveryManId));

        // Assert
        assertTrue(result);
    }

    @Test
    void testDeleteDeliveryMan_InvalidInput_ReturnsFalse() {
        // Arrange
        String invalidDeliveryManId = "invalid-id";

        // Act
        boolean result = deliveryManGateway.deleteDeliveryMan(invalidDeliveryManId);

        // Assert
        assertFalse(result);
    }

    @Test
    void testFindDeliveryMan_ValidInput_ReturnsDeliveryMan() {
        // Arrange
        UUID deliveryManId = UUID.randomUUID();
        DeliveryMan expectedDeliveryMan = new DeliveryMan();
        // Mock the behavior of deliveryManRepository
        when(deliveryManRepository.findById(deliveryManId)).thenReturn(Optional.of(expectedDeliveryMan));

        // Act
        DeliveryMan result = deliveryManGateway.findDeliveryMan(String.valueOf(deliveryManId));

        // Assert
        assertEquals(expectedDeliveryMan, result);
    }

    @Test
    void testFindDeliveryMan_InvalidInput_ReturnsNull() {
        // Arrange
        String invalidDeliveryManId = "invalid-id";

        // Act
        DeliveryMan result = deliveryManGateway.findDeliveryMan(invalidDeliveryManId);

        // Assert
        assertNull(result);
    }

    @Test
    void testListAllDeliveryMans_ReturnsListOfDeliveryMans() {
        // Arrange
        List<DeliveryMan> expectedDeliveryMans = List.of(new DeliveryMan(), new DeliveryMan());
        // Mock the behavior of deliveryManRepository
        when(deliveryManRepository.findAll()).thenReturn(expectedDeliveryMans);

        // Act
        List<DeliveryMan> result = deliveryManGateway.listAllDeliveryMans();

        // Assert
        assertEquals(expectedDeliveryMans, result);
    }
}
