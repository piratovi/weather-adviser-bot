package com.kolosov.openmeteosdk;

import com.kolosov.openmeteosdk.api.OpenMeteoAPIClient;
import com.kolosov.openmeteosdk.api.OpenMeteoResponse.OpenMeteoHourlyForecast;
import com.kolosov.openmeteosdk.api.WeatherDayData;
import com.kolosov.openmeteosdk.api.WeatherDayData.WeatherHourData;
import com.kolosov.openmeteosdk.mapper.OpenMeteoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
public class OpenMeteoService {

    private final OpenMeteoAPIClient client;
    private final OpenMeteoMapper mapper;

    public SortedSet<WeatherDayData> getWeekForecast(Location location) {
        var openMeteoResponse = client.getRawForecast(location.latitude(), location.longitude());
        return groupHourlyForecastsByDay(openMeteoResponse.hourly());
    }

    private SortedSet<WeatherDayData> groupHourlyForecastsByDay(OpenMeteoHourlyForecast hourlyForecast) {
        int hoursCount = hourlyForecast.time().size();
        Map<LocalDate, SortedSet<WeatherHourData>> map = IntStream.range(0, hoursCount)
                .boxed()
                .map(i -> mapper.toIntermediateDTO(hourlyForecast, i))
                .collect(Collectors.groupingBy(
                        mapper::getLocalDate,
                        Collectors.mapping(mapper::toWeatherHourData, toCollection(TreeSet::new))
                ));
        return map.entrySet().stream()
                .map(e -> new WeatherDayData(e.getKey(), e.getValue()))
                .collect(toCollection(TreeSet::new));
    }

}
