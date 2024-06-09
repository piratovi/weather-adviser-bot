package com.kolosov.openmeteosdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record OpenMeteoResponse(OpenMeteoHourlyForecast hourly) {

    public record OpenMeteoHourlyForecast(
            List<LocalDateTime> time,
            List<Double> precipitation,
            @JsonProperty("precipitation_probability") List<Integer> precipitationProbability,
            @JsonProperty("temperature_2m") List<Double> temperature,
            @JsonProperty("apparent_temperature") List<Double> apparentTemperature,
            @JsonProperty("relative_humidity_2m") List<Integer> relativeHumidity,
            @JsonProperty("cloud_cover") List<Integer> cloudCover,
            @JsonProperty("wind_speed_10m") List<Double> windSpeed,
            @JsonProperty("wind_gusts_10m") List<Double> windGusts
    ) {
    }
}
