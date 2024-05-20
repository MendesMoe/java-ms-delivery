package com.postech.msdelivery.usecase;


import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DirectionsUseCase {

    @Value("${google.maps.api.key}")
    private String apiKey;
    @Value("${origin.address}")
    private String originAddress;

    public long calculateRoute(String destination) throws IOException, InterruptedException, ApiException {

        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(apiKey)
                    .build();

            DirectionsResult directionsResult;

            directionsResult = DirectionsApi.newRequest(context)
                    .origin(originAddress)
                    .destination(destination)
                    .mode(TravelMode.DRIVING)
                    .await();
            context.shutdown();
            return directionsResult.routes[0].legs[0].duration.inSeconds;

        } catch (Exception e) {
            log.error("Error calculating route {}", e.getMessage());
            throw e;
        }

    }
}
