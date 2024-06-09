package com.kolosov.openmeteosdk.api;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.math.BigDecimal;
import java.util.List;

//TODO Deal with constant RequestParams
public interface OpenMeteoAPIClient {

    String OPEN_METEO_API_URL = "https://api.open-meteo.com/v1";

    @GetExchange(url = "/forecast")
    OpenMeteoResponse getRawForecast(
            @RequestParam("latitude") BigDecimal latitude,
            @RequestParam("longitude") BigDecimal longitude,
            @RequestParam("hourly") List<String> hourly,
            @RequestParam(value = "timezone", defaultValue = "Asia/Bangkok") String timezone
    );

    default OpenMeteoResponse getRawForecast(BigDecimal latitude, BigDecimal longitude) {
        List<String> hourly = List.of(
                "precipitation",
                "temperature_2m",
                "apparent_temperature",
                "relative_humidity_2m",
                "cloud_cover",
                "precipitation_probability",
                "wind_speed_10m",
                "wind_gusts_10m"
        );
        return getRawForecast(latitude, longitude, hourly, "Asia/Bangkok");
    }
}
