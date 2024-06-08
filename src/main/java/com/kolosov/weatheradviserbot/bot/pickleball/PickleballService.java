package com.kolosov.weatheradviserbot.bot.pickleball;

import com.kolosov.openmeteosdk.OpenMeteoService;
import com.kolosov.openmeteosdk.api.WeatherDayData;
import com.kolosov.openmeteosdk.api.WeatherDayData.WeatherHourData;
import com.kolosov.weatheradviserbot.analayze.Analysis;
import com.kolosov.weatheradviserbot.analayze.AnalyzerCommand;
import com.kolosov.weatheradviserbot.analayze.PickleballAnalyzerCommand;
import com.kolosov.weatheradviserbot.bot.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PickleballService implements ForecastService {

    private final OpenMeteoService openMeteoService;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.0");
    private final DecimalFormat wideDecimalFormat = new DecimalFormat("00.0");

    @Override
    public String getForecast() {
        SortedSet<WeatherDayData> forecastData = openMeteoService.getPrecipitationForecastForPickleball();
        return composeFullForecast(forecastData);
    }

    private String composeFullForecast(SortedSet<WeatherDayData> set) {
        return set.stream()
                .map(this::filterAndComposeDayForecast)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String filterAndComposeDayForecast(WeatherDayData data) {
        WeatherDayData filteredData = filterTime(data, this::filterPickleballTime);
        return composeDayForecast(filteredData);
    }

    private String composeDayForecast(WeatherDayData data) {
        DayOfWeek dayOfWeek = data.day().getDayOfWeek();
        AnalyzerCommand analyzerCommand = new PickleballAnalyzerCommand(data);
        Analysis analysis = analyzerCommand.analyze();
        String weatherData = formatDetailedWeatherData(data);
        return """
                %s. %s. %s
                %s""".formatted(
                dayOfWeek, analysis.result(), analysis.description(),

                weatherData);
    }

    private boolean filterPickleballTime(WeatherHourData weatherHourData) {
        int hour = weatherHourData.time().getHour();
        return isEveningHour(hour);
    }

    private boolean isEveningHour(int hour) {
        return hour >= 16 && hour <= 20;
    }


    private String formatDetailedWeatherData(WeatherDayData weatherDayData) {
        String header = "Ho Prec PP Tem Hum Clo Wi WiG";
        String detailedData = weatherDayData.weatherHourData().stream()
                .map(this::formatWeatherHourDataLine)
                .collect(Collectors.joining(System.lineSeparator()));
        return header + System.lineSeparator() + detailedData;
    }

    String formatWeatherHourDataLine(WeatherHourData weatherHourData) {
        String hour = formatHour(weatherHourData.time());
        String precipitationFormatted = decimalFormat.format(weatherHourData.precipitation());
        int temperature = (int) weatherHourData.temperature();
        int apparentTemperature = (int) weatherHourData.apparentTemperature();
        int relativeHumidity = weatherHourData.relativeHumidity();
        int cloudCover = weatherHourData.cloudCover();
        int precipitationProbability = weatherHourData.precipitationProbability();
        double windSpeed = weatherHourData.windSpeed();
        double windGusts = weatherHourData.windsGusts();
        return "%s %s %2d %d %d %d %3d %2.0f %2.0f".formatted(hour, precipitationFormatted, precipitationProbability,
                temperature, apparentTemperature, relativeHumidity, cloudCover, windSpeed, windGusts);
    }

    String formatHour(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern("HH"));
    }

    String formatDay(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("EEE"));
    }

    private boolean isWeekDay(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> true;
            default -> false;
        };
    }

}
