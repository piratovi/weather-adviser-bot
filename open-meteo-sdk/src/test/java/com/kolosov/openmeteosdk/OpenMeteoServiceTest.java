package com.kolosov.openmeteosdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolosov.openmeteosdk.api.WeatherDayData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.SortedSet;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
class OpenMeteoServiceTest {

    @Autowired
    OpenMeteoService service;

    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void getWeekForecast() {
        // setup

        // act
        SortedSet<WeatherDayData> weekForecast = service.getWeekForecast(Location.pickleball());
        System.out.println(objectMapper.writeValueAsString(weekForecast));

        // verify

    }
}