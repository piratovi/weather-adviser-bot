package com.kolosov.weatheradviserbot.openMeteo;

import com.kolosov.weatheradviserbot.openMeteo.WeatherDayData.WeatherHourData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kolosov.weatheradviserbot.openMeteo.Location.PICKLEBALL;
import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
//TODO Refactor
public class OpenMeteoService {

    private final OpenMeteoAPIClient client;

    public SortedSet<WeatherDayData> getPrecipitationForecastForPickleball() {
        OpenMeteoResponse perceptionForecast = getPerceptionForecast(PICKLEBALL);
        return mapResponse(perceptionForecast.hourly());
    }

    OpenMeteoResponse getPerceptionForecast(Location location) {
        return client.getRawForecast(location.getLatitude(), location.getLongitude());
    }

    private SortedSet<WeatherDayData> mapResponse(OpenMeteoResponse.Hourly container) {
        Map<LocalDate, SortedSet<WeatherHourData>> map = IntStream.range(0, container.time().size())
                .boxed()
                .map(i -> mapForecastDTO(container, i))
                .collect(Collectors.groupingBy(
                        f -> f.time.toLocalDate(),
                        Collectors.mapping(this::mapForecastHourData, toCollection(TreeSet::new))
                ));
        return map.entrySet().stream()
                .map(e -> new WeatherDayData(e.getKey(), e.getValue()))
                .collect(toCollection(TreeSet::new));
    }

    private record ForecastDTO(LocalDateTime time,
                               double precipitation,
                               double temperature,
                               double apparentTemperature,
                               int relativeHumidity,
                               int cloudCover,
                               int precipitationProbability,
                               double windSpeed,
                               double windGusts) {
    }

    private ForecastDTO mapForecastDTO(OpenMeteoResponse.Hourly container, Integer index) {
        return new ForecastDTO(
                container.time().get(index),
                container.precipitation().get(index),
                container.temperature_2m().get(index),
                container.apparent_temperature().get(index),
                container.relative_humidity_2m().get(index),
                container.cloud_cover().get(index),
                container.precipitation_probability().get(index),
                container.wind_speed_10m().get(index),
                container.wind_gusts_10m().get(index)
        );
    }

    private WeatherHourData mapForecastHourData(ForecastDTO forecastDTO) {
        return new WeatherHourData(
                forecastDTO.time().toLocalTime(),
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
