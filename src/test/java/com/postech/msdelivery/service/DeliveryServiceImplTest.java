package com.postech.msdelivery.service;


import com.postech.msdelivery.client.GoogleDirectionsClient;
import com.postech.msdelivery.client.Order;
import com.postech.msdelivery.client.OrderClient;
import com.postech.msdelivery.client.Route;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryStatus;
import com.postech.msdelivery.exception.ResourceNotFoundException;
import com.postech.msdelivery.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private OrderClient orderClient;
    @Mock
    private GoogleDirectionsClient googleDirectionsApi;

    @InjectMocks
    private DeliveryService deliveryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDelivery() {
        // Arrange
        Long orderId = 123L;
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerAddress("123 Main St");
        Route route = new Route();
        route.setDurationInSeconds(Duration.ofMinutes(30).getSeconds());

        when(orderClient.getOrderById(orderId)).thenReturn(order);
        when(googleDirectionsApi.calculateRoute(anyString(), anyString())).thenReturn(route);
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Delivery createdDelivery = deliveryService.createDelivery(orderId);

        // Assert
        assertEquals(orderId, createdDelivery.getOrderId());
        assertEquals("123 Main St", createdDelivery.getCustomerAddress());
        assertEquals(DeliveryStatus.PLACED, createdDelivery.getStatus());
       // assertEquals(route, createdDelivery.getRoute());
        // Assert estimated delivery time is calculated correctly
    }

    @Test
    public void testGetDeliveryById_Success() {
        // Arrange
        Long deliveryId = 1L;
        Delivery delivery = new Delivery();
        delivery.setId(deliveryId);
        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(delivery));

        // Act
        Delivery result = deliveryService.getDeliveryById(deliveryId);

        // Assert
        assertEquals(delivery, result);
    }

    @Test
    public void testGetDeliveryById_NotFound() {
        // Arrange
        Long deliveryId = 1L;
        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> deliveryService.getDeliveryById(deliveryId));
    }
}
