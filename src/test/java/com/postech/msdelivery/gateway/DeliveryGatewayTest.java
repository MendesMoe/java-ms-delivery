package com.postech.msdelivery.gateway;

import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DeliveryGatewayTest {
    @InjectMocks
    private DeliveryGateway deliveryGateway;

    @Mock
    private DeliveryRepository deliveryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDelivery_ValidInput_ReturnsDelivery() {
        Delivery delivery = new Delivery();
        when(deliveryRepository.save(delivery)).thenReturn(delivery);
        Delivery result = deliveryGateway.createDelivery(delivery);
        assertEquals(delivery, result);
    }

    @Test
    void testUpdateDelivery_ValidInput_ReturnsDelivery() {
        Delivery delivery = new Delivery();
        when(deliveryRepository.save(delivery)).thenReturn(delivery);
        Delivery result = deliveryGateway.updateDelivery(delivery);
        assertEquals(delivery, result);
    }

    @Test
    void testDeleteDelivery_ValidInput_ReturnsTrue() {
        UUID deliveryId = UUID.randomUUID();
        doNothing().when(deliveryRepository).deleteById(deliveryId);
        boolean result = deliveryGateway.deleteDelivery(String.valueOf(deliveryId));
        assertTrue(result);
    }

    @Test
    void testDeleteDelivery_InvalidInput_ReturnsFalse() {
        String invalidDeliveryId = "invalid-id";
        boolean result = deliveryGateway.deleteDelivery(invalidDeliveryId);
        assertFalse(result);
    }

    @Test
    void testFindDelivery_ValidInput_ReturnsDelivery() {
        UUID deliveryId = UUID.randomUUID();
        Delivery expectedDelivery = new Delivery();
        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(expectedDelivery));
        Delivery result = deliveryGateway.findDelivery(String.valueOf(deliveryId));
        assertEquals(expectedDelivery, result);
    }

    @Test
    void testFindDelivery_InvalidInput_ReturnsNull() {
        String invalidDeliveryId = "invalid-id";
        Delivery result = deliveryGateway.findDelivery(invalidDeliveryId);
        assertNull(result);
    }

    @Test
    void testListAllDeliverys_ReturnsListOfDeliverys() {
        List<Delivery> expectedDeliverys = List.of(new Delivery(), new Delivery());
        when(deliveryRepository.findAll()).thenReturn(expectedDeliverys);
        List<Delivery> result = deliveryGateway.listAllDeliverys();
        assertEquals(expectedDeliverys, result);
    }

}
