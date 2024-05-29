package com.kolosov.weatheradviserbot.analayze;

import com.kolosov.weatheradviserbot.openMeteo.WeatherDayData;
import com.kolosov.weatheradviserbot.openMeteo.WeatherDayData.WeatherHourData;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.kolosov.weatheradviserbot.analayze.Analysis.Result.*;

@RequiredArgsConstructor
public class PickleballAnalyzerCommand implements AnalyzerCommand {

    private final WeatherDayData weatherDayData;

    @Override
    public Analysis analyze() {
        SortedSet<WeatherHourData> dataSet = weatherDayData.weatherHourData();
        List<Analysis> list = getAllAnalyses(dataSet);
        Analysis.Result worstResult = list.stream()
                .map(Analysis::result)
                .min(Comparator.comparingInt(Enum::ordinal))
                .orElseThrow();
        String description = list.stream()
                .map(Analysis::description)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(". "));
        return new Analysis(worstResult, description);
    }

    private List<Analysis> getAllAnalyses(SortedSet<WeatherHourData> dataSet) {
        Analysis precipitationAnalysis = analyzePrecipitation(dataSet);
        Analysis temperatureAnalysis = analyzeTemperature(dataSet);
        Analysis cloudCoverAnalysis = analyzeCloudCover(dataSet);
        Analysis windAnalysis = analyzeWind(dataSet);
        return List.of(precipitationAnalysis, temperatureAnalysis, cloudCoverAnalysis, windAnalysis);
    }

    private Analysis analyzeWind(SortedSet<WeatherHourData> dataSet) {
        double windSpeed = dataSet.stream()
                .mapToDouble(WeatherHourData::windSpeed)
                .max()
                .orElseThrow();
        double windsGusts = dataSet.stream()
                .mapToDouble(WeatherHourData::windsGusts)
                .max()
                .orElseThrow();
        if (windSpeed < 10 && windsGusts < 15) {
            return new Analysis(PERFECT, null);
        }
        if (windSpeed < 20 && windsGusts < 25) {
            return new Analysis(GOOD, "Small wind");
        }
        if (windSpeed < 30 && windsGusts < 40) {
            return new Analysis(UNDEFINED, "Wind");
        }
        return new Analysis(BAD, "Strong Wind");
    }

    private Analysis analyzeTemperature(SortedSet<WeatherHourData> dataSet) {
        double temperatureAvg = dataSet.stream()
                .mapToDouble(WeatherHourData::apparentTemperature)
                .average()
                .orElseThrow();
        if (temperatureAvg <= 29) {
            return new Analysis(PERFECT, "Cool temperature");
        }
        if (temperatureAvg <= 33) {
            return new Analysis(GOOD, null);
        }
        return new Analysis(UNDEFINED, "Hot");
    }

    private Analysis analyzePrecipitation(SortedSet<WeatherHourData> dataSet) {
        double precipitationSum = dataSet.stream()
                .mapToDouble(WeatherHourData::precipitation)
                .sum();
        if (precipitationSum == 0) {
            return new Analysis(PERFECT, null);
        }
        if (precipitationSum <= 1) {
            return new Analysis(GOOD, "Small precipitations");
        }
        if (precipitationSum <= 2) {
            return new Analysis(UNDEFINED, "Precipitations");
        }
        return new Analysis(BAD, "Rain");
    }

    private Analysis analyzeCloudCover(SortedSet<WeatherHourData> dataSet) {
        double cloudCoverageAvg = dataSet.stream()
                .mapToInt(WeatherHourData::cloudCover)
                .average()
                .orElseThrow();
        if (cloudCoverageAvg > 80) {
            return new Analysis(PERFECT, "Cloudy");
        }
        return new Analysis(PERFECT, null);
    }
}
