package com.kolosov.weatheradviserbot;

import com.kolosov.weatheradviserbot.openMeteo.OpenMeteoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class WeatherAdviserBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherAdviserBotApplication.class, args);
    }

}
