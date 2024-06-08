package com.kolosov.openmeteosdk.api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.SortedSet;

//TODO Refactor
public record WeatherDayData(LocalDate day, SortedSet<WeatherHourData> weatherHourData) implements Comparable<WeatherDayData> {

    @Override
    public int compareTo(WeatherDayData o) {
        return this.day().compareTo(o.day());
    }

    public record WeatherHourData(
            LocalTime time,
            double precipitation,
            double temperature,
            double apparentTemperature,
            int relativeHumidity,
            int cloudCover,
            int precipitationProbability,
            double windSpeed,
            double windsGusts
    ) implements Comparable<WeatherHourData> {

        @Override
        public int compareTo(WeatherHourData weatherHourData) {
            return this.time().compareTo(weatherHourData.time());
        }

    }
}