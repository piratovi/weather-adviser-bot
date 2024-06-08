package com.kolosov.weatheradviserbot;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.kolosov.weatheradviserbot",
        "com.kolosov.openmeteosdk"
})
public class WeatherAdviserBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherAdviserBotApplication.class, args);
    }

}
