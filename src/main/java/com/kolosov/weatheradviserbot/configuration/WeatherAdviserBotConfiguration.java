package com.kolosov.weatheradviserbot.configuration;

import com.kolosov.weatheradviserbot.bot.WeatherAdviserBot;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class WeatherAdviserBotConfiguration {

    @SneakyThrows
    @Bean
    TelegramBotsApi telegramBotsApi(WeatherAdviserBot bot) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        return telegramBotsApi;
    }
}
