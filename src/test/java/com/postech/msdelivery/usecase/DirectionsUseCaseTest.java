package com.postech.msdelivery.usecase;


import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DirectionsUseCaseTest {

    @Mock
    private GeoApiContext geoApiContext;

    @Mock
    private DirectionsApiRequest directionsApiRequest;

    @InjectMocks
    private DirectionsUseCase directionsUseCase;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(directionsUseCase, "apiKey", "AIzaSyAJsgVQ7u5xrU1YNnPTe76imES7cchodj0");
        ReflectionTestUtils.setField(directionsUseCase, "originAddress", "Praca da Se, 68 Sao Paulo - SP, CEP 01001-001");
    }

    @Test
    public void testCalculateRouteSuccess() throws Exception {
        // Arrange
        String destination = "cep 01151010";
        DirectionsResult mockResult = new DirectionsResult();
        mockResult.routes = new DirectionsRoute[1];
        mockResult.routes[0] = new DirectionsRoute();
        mockResult.routes[0].legs = new DirectionsLeg[1];
        mockResult.routes[0].legs[0] = new DirectionsLeg();
        mockResult.routes[0].legs[0].duration = new Duration();
        mockResult.routes[0].legs[0].duration.inSeconds = 1800; // 30 minutes



        // Act
        long duration = directionsUseCase.calculateRoute(destination);

        // Assert
        assertNotNull(duration);
    }

    @Test
    public void testCalculateRouteFailure() throws Exception {
        // Arrange
        String destination = "Destination Address";



        // Act & Assert
        assertThrows(NotFoundException.class, () -> directionsUseCase.calculateRoute(destination));
    }
}
