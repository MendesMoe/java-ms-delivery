package com.postech.msdelivery.gateway;

import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.repository.DeliveryRepository;
import com.postech.msdelivery.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DeliveryServiceTest {
    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDelivery_ValidInput_ReturnsDelivery() {
        // Arrange
        Delivery delivery = new Delivery();
        // Mock the behavior of deliveryRepository
        when(deliveryRepository.save(delivery)).thenReturn(delivery);

        // Act
        Delivery result = deliveryService.createDelivery(delivery);

        // Assert
        assertEquals(delivery, result);
    }

    @Test
    void testUpdateDelivery_ValidInput_ReturnsDelivery() {
        // Arrange
        Delivery delivery = new Delivery();
        // Mock the behavior of deliveryRepository
        when(deliveryRepository.save(delivery)).thenReturn(delivery);

        // Act
        Delivery result = deliveryService.updateDelivery(delivery);

        // Assert
        assertEquals(delivery, result);
    }

    @Test
    void testDeleteDelivery_ValidInput_ReturnsTrue() {
        // Arrange
        Long deliveryId = 345l;
        // Mock the behavior of deliveryRepository
        doNothing().when(deliveryRepository).deleteById(deliveryId);

        // Act
        boolean result = deliveryService.deleteDelivery(deliveryId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testDeleteDelivery_InvalidInput_ReturnsFalse() {
        // Arrange
        Long invalidDeliveryId = 123l;

        // Act
        boolean result = deliveryService.deleteDelivery(invalidDeliveryId);

        // Assert
        assertFalse(result);
    }

    @Test
    void testFindDelivery_ValidInput_ReturnsDelivery() {
        // Arrange
        Long deliveryId = 345l;
        Delivery expectedDelivery = new Delivery();
        // Mock the behavior of deliveryRepository
        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(expectedDelivery));

        // Act
        Delivery result = deliveryService.findDelivery(deliveryId);

        // Assert
        assertEquals(expectedDelivery, result);
    }

    @Test
    void testFindDelivery_InvalidInput_ReturnsNull() {
        // Arrange
        Long invalidDeliveryId = 5l;
        // Act
        Delivery result = deliveryService.findDelivery(invalidDeliveryId);

        // Assert
        assertNull(result);
    }

    @Test
    void testListAllDeliverys_ReturnsListOfDeliverys() {
        // Arrange
        List<Delivery> expectedDeliverys = List.of(new Delivery(), new Delivery());
        // Mock the behavior of deliveryRepository
        when(deliveryRepository.findAll()).thenReturn(expectedDeliverys);

        // Act
        List<Delivery> result = deliveryService.listAllDeliverys();

        // Assert
        assertEquals(expectedDeliverys, result);
    }
}
