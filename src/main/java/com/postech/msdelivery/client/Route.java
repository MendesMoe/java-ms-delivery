package com.postech.msdelivery.client;

import com.google.maps.model.Distance;
import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Embeddable
@Data
@RequiredArgsConstructor
public class Route {

    @Transient
    private List<LatLng> waypoints;

    private Long durationInSeconds;

    private Long distanceInMeters;

    public Route(List<LatLng> waypoints, Duration duration, Distance distance) {
        this.waypoints = waypoints;
        this.durationInSeconds = duration.inSeconds;
        this.distanceInMeters = distance.inMeters;
    }

}
