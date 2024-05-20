package com.postech.msdelivery.service;


import com.postech.msdelivery.client.*;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryStatus;
import com.postech.msdelivery.exception.ResourceNotFoundException;
import com.postech.msdelivery.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@TestPropertySource(locations = "classpath:application-test.properties")
public class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private OrderClient orderClient;
    @Mock
    private CustomerClient customerClient;
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
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        UUID customerId = UUID.randomUUID();
        order.setIdCustomer(customerId);
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setEndereco("Rua Olimpia de Almeida Prado, 27");
        Route route = new Route();
        route.setDurationInSeconds(Duration.ofMinutes(30).getSeconds());

        when(orderClient.getOrderById(orderId.toString())).thenReturn(order);
        when(customerClient.getCustomer(customer.getId().toString())).thenReturn(customer);
        when(googleDirectionsApi.calculateRoute(anyString(), anyString())).thenReturn(route);
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Delivery createdDelivery = deliveryService.createDelivery(orderId.toString());

        // Assert
        assertEquals(orderId, createdDelivery.getOrderUuid());
        assertEquals("Rua Olimpia de Almeida Prado, 27, , CEP", createdDelivery.getCustomerAddress());
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
