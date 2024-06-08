package com.kolosov.weatheradviserbot.openMeteo;

import com.kolosov.openmeteosdk.OpenMeteoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.kolosov.openmeteosdk.Location.PICKLEBALL;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
class OpenMeteoServiceTest {

    @Autowired
    OpenMeteoService service;

    @Test
    public void getPerceptionForecast() {
        // setup

        // act
        System.out.println(service.getPerceptionForecast(PICKLEBALL));

        // verify

    }
}