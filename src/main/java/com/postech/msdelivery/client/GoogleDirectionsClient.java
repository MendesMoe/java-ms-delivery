package com.postech.msdelivery.client;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.postech.msdelivery.exception.ErrorCalculatingRouteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class GoogleDirectionsClient {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public Route calculateRoute(String origin, String destination) {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        DirectionsResult directionsResult;
        try {
            directionsResult = DirectionsApi.newRequest(context)
                    .origin(origin)
                    .destination(destination)
                    .mode(TravelMode.DRIVING)
                    .await();
            context.shutdown();
            return new Route(
                    directionsResult.routes[0].overviewPolyline.decodePath(),
                    directionsResult.routes[0].legs[0].duration,
                    directionsResult.routes[0].legs[0].distance
            );
        } catch (ApiException | InterruptedException | IOException e) {
            log.error("Error calculating route {}", e.getMessage());
            throw new ErrorCalculatingRouteException(e.getMessage());
        }

    }
}

