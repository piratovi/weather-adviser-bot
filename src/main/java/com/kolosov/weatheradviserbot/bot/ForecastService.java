package com.kolosov.weatheradviserbot.bot;


import com.kolosov.openmeteosdk.api.WeatherDayData;
import com.kolosov.openmeteosdk.api.WeatherDayData.WeatherHourData;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface ForecastService {
    String getForecast();

    default WeatherDayData filterTime(WeatherDayData data, Predicate<WeatherHourData> predicate) {
        SortedSet<WeatherHourData> filteredHoursData = data.weatherHourData().stream()
                .filter(predicate)
                .collect(Collectors.toCollection(TreeSet::new));
        return new WeatherDayData(data.day(), filteredHoursData);
    }
}
