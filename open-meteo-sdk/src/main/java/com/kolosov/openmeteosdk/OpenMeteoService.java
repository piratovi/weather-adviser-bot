package com.kolosov.openmeteosdk;

import com.kolosov.openmeteosdk.api.OpenMeteoResponse.OpenMeteoHourlyForecast;
import com.kolosov.openmeteosdk.api.WeatherDayData;
import com.kolosov.openmeteosdk.api.WeatherDayData.WeatherHourData;
import com.kolosov.openmeteosdk.api.OpenMeteoAPIClient;
import com.kolosov.openmeteosdk.api.OpenMeteoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
//TODO Refactor
public class OpenMeteoService {

    private final OpenMeteoAPIClient client;

    public SortedSet<WeatherDayData> getWeekForecastForPickleball() {
        Location pickleball = Location.pickleball();
        var openMeteoResponse = client.getRawForecast(pickleball.latitude(), pickleball.longitude());
        return groupHourlyForecastsByDay(openMeteoResponse.hourly());
    }

    public SortedSet<WeatherDayData> getWeekForecast(Location location) {
        var openMeteoResponse = client.getRawForecast(location.latitude(), location.longitude());
        return groupHourlyForecastsByDay(openMeteoResponse.hourly());
    }

    private SortedSet<WeatherDayData> groupHourlyForecastsByDay(OpenMeteoHourlyForecast hourlyForecast) {
        Map<LocalDate, SortedSet<WeatherHourData>> map = IntStream.range(0, hourlyForecast.time().size())
                .boxed()
                .map(i -> mapForecastDTO(hourlyForecast, i))
                .collect(Collectors.groupingBy(
                        f -> f.dateTime.toLocalDate(),
                        Collectors.mapping(this::mapForecastHourData, toCollection(TreeSet::new))
                ));
        return map.entrySet().stream()
                .map(e -> new WeatherDayData(e.getKey(), e.getValue()))
                .collect(toCollection(TreeSet::new));
    }

    private record ForecastDTO(LocalDateTime dateTime,
                               double precipitation,
                               double temperature,
                               double apparentTemperature,
                               int relativeHumidity,
                               int cloudCover,
                               int precipitationProbability,
                               double windSpeed,
                               double windGusts) {
    }

    private ForecastDTO mapForecastDTO(OpenMeteoHourlyForecast hourlyForecast, int index) {
        return new ForecastDTO(
                hourlyForecast.time().get(index),
                hourlyForecast.precipitation().get(index),
                hourlyForecast.temperature_2m().get(index),
                hourlyForecast.apparent_temperature().get(index),
                hourlyForecast.relative_humidity_2m().get(index),
                hourlyForecast.cloud_cover().get(index),
                hourlyForecast.precipitation_probability().get(index),
                hourlyForecast.wind_speed_10m().get(index),
                hourlyForecast.wind_gusts_10m().get(index)
        );
    }

    private WeatherHourData mapForecastHourData(ForecastDTO forecastDTO) {
        return new WeatherHourData(
                forecastDTO.dateTime().toLocalTime(),
                forecastDTO.precipitation(),
                forecastDTO.temperature(),
                forecastDTO.apparentTemperature(),
                forecastDTO.relativeHumidity(),
                forecastDTO.cloudCover(),
                forecastDTO.precipitationProbability(),
                forecastDTO.windSpeed(),
                forecastDTO.windGusts()
        );
    }

}
