package com.postech.msdelivery.client;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
                    .mode(TravelMode.DRIVING) // Adjust as needed
                    .await();
        } catch (ApiException | InterruptedException | IOException e) {
            // Handle exceptions (e.g., log error, throw custom exception)
            throw new RuntimeException("Error calculating route", e);
        }

        // Extract route information from the result
        return new Route(
                directionsResult.routes[0].overviewPolyline.decodePath(),
                directionsResult.routes[0].legs[0].duration,
                directionsResult.routes[0].legs[0].distance
        );
    }
}

