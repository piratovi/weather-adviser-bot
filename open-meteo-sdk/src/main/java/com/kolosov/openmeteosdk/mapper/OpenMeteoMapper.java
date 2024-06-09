package com.kolosov.openmeteosdk.mapper;

import com.kolosov.openmeteosdk.api.OpenMeteoResponse.OpenMeteoHourlyForecast;
import com.kolosov.openmeteosdk.api.WeatherDayData.WeatherHourData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface OpenMeteoMapper {

    record IntermediateForecastDTO(
            LocalDateTime dateTime,
            double precipitation,
            double temperature,
            double apparentTemperature,
            int relativeHumidity,
            int cloudCover,
            int precipitationProbability,
            double windSpeed,
            double windGusts
    ) {}

    default IntermediateForecastDTO toIntermediateDTO(OpenMeteoHourlyForecast hourlyForecast, int index) {
        return new IntermediateForecastDTO(
                hourlyForecast.time().get(index),
                hourlyForecast.precipitation().get(index),
                hourlyForecast.temperature().get(index),
                hourlyForecast.apparentTemperature().get(index),
                hourlyForecast.relativeHumidity().get(index),
                hourlyForecast.cloudCover().get(index),
                hourlyForecast.precipitationProbability().get(index),
                hourlyForecast.windSpeed().get(index),
                hourlyForecast.windGusts().get(index)
        );
    }

    @Mapping(target = "time", source = "dateTime")
    WeatherHourData toWeatherHourData(IntermediateForecastDTO intermediateForecastDTO);

    // Custom mapping method for LocalDateTime to LocalTime
    default LocalTime mapLocalDateTimeToLocalTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }

}
