package com.kolosov.openmeteosdk.api;

import java.time.LocalDateTime;
import java.util.List;

public record OpenMeteoResponse(Hourly hourly) {

    public record Hourly(
            List<LocalDateTime> time,
            List<Double> precipitation,
            List<Integer> precipitation_probability,
            List<Double> temperature_2m,
            List<Double> apparent_temperature,
            List<Integer> relative_humidity_2m,
            List<Integer> cloud_cover,
            List<Double> wind_speed_10m,
            List<Double> wind_gusts_10m
    ) {}
}
