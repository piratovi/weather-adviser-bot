package com.kolosov.openmeteosdk.mapper;

import com.kolosov.openmeteosdk.api.WeatherDayData;
import java.time.LocalTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-09T16:04:32+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Amazon.com Inc.)"
)
@Component
public class OpenMeteoMapperImpl implements OpenMeteoMapper {

    @Override
    public WeatherDayData.WeatherHourData toWeatherHourData(OpenMeteoMapper.IntermediateForecastDTO intermediateForecastDTO) {
        if ( intermediateForecastDTO == null ) {
            return null;
        }

        LocalTime time = null;
        double precipitation = 0.0d;
        double temperature = 0.0d;
        double apparentTemperature = 0.0d;
        int relativeHumidity = 0;
        int cloudCover = 0;
        int precipitationProbability = 0;
        double windSpeed = 0.0d;
        double windGusts = 0.0d;

        time = mapLocalDateTimeToLocalTime( intermediateForecastDTO.dateTime() );
        precipitation = intermediateForecastDTO.precipitation();
        temperature = intermediateForecastDTO.temperature();
        apparentTemperature = intermediateForecastDTO.apparentTemperature();
        relativeHumidity = intermediateForecastDTO.relativeHumidity();
        cloudCover = intermediateForecastDTO.cloudCover();
        precipitationProbability = intermediateForecastDTO.precipitationProbability();
        windSpeed = intermediateForecastDTO.windSpeed();
        windGusts = intermediateForecastDTO.windGusts();

        WeatherDayData.WeatherHourData weatherHourData = new WeatherDayData.WeatherHourData( time, precipitation, temperature, apparentTemperature, relativeHumidity, cloudCover, precipitationProbability, windSpeed, windGusts );

        return weatherHourData;
    }
}
