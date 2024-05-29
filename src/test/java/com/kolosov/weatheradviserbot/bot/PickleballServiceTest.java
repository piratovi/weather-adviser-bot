package com.kolosov.weatheradviserbot.bot;

import com.kolosov.weatheradviserbot.bot.pickleball.PickleballService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class PickleballServiceTest {

    @Autowired
    PickleballService service;

    @Test
    public void formatForecastLine() {
        // setup

        // act
//        System.out.println(service.formatForecastLine(Map.entry(LocalDateTime.now(), 222.22)));
//        System.out.println(service.formatForecastLine(Map.entry(LocalDateTime.now(), 22.22)));
//        System.out.println(service.formatForecastLine(Map.entry(LocalDateTime.now(), 2.22)));
//        System.out.println(service.formatForecastLine(Map.entry(LocalDateTime.now(), 0.22)));
//        System.out.println(service.formatForecastLine(Map.entry(LocalDateTime.now(), 0.2)));
//        System.out.println(service.formatForecastLine(Map.entry(LocalDateTime.now(), 0.0)));

        // verify
    }

    @Test
    public void getForecast() {
        // setup

        // act
        String forecast = service.getForecast();

        // verify
        System.out.println(forecast);
    }

}