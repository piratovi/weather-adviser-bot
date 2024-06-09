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
    public void getForecast() {
        // setup

        // act
        String forecast = service.getForecast();

        // verify
        System.out.println(forecast);
    }

}