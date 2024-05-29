package com.kolosov.weatheradviserbot.bot;

import com.kolosov.weatheradviserbot.bot.pickleball.PickleballService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
public class WeatherAdviserBot extends TelegramLongPollingBot {

    private final Map<String, ForecastService> registeredCommands;
    private final String greetings;

    public WeatherAdviserBot(@Value("${bot.token}") String botToken, PickleballService pickleballService) {
        super(botToken);
        registeredCommands = registerCommands(pickleballService);
        greetings = prepareGreetings(registeredCommands);
    }

    private Map<String, ForecastService> registerCommands(PickleballService pickleballService) {
        return Map.of(
                "/p", pickleballService,
                "/pickleball", pickleballService
        );
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processReceivedMessage(update.getMessage());
        }
    }

    @SneakyThrows
    private void processReceivedMessage(Message userMessage) {
        String response = prepareResponse(userMessage.getText());
        String chatId = userMessage.getChatId().toString();
        SendMessage sendMessage = new SendMessage(chatId, response);
        execute(sendMessage);
    }

    private String prepareResponse(String userCommand) {
        ForecastService forecastService = registeredCommands.get(userCommand);
        return Optional.ofNullable(forecastService)
                .map(ForecastService::getForecast)
                .orElse(greetings);
    }

    private String prepareGreetings(Map<String, ForecastService> commandToForecastService) {
        String header = "Available commands:" + System.lineSeparator();
        String availableCommands = getForecastToCommandMap(commandToForecastService).values().stream()
                .map(commands -> String.join(", ", commands))
                .collect(Collectors.joining(System.lineSeparator()));
        return header + availableCommands;
    }

    private Map<ForecastService, Set<String>> getForecastToCommandMap(Map<String, ForecastService> commandToForecastService) {
        return commandToForecastService.entrySet().stream()
                .collect(
                        Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, toSet())));
    }

    @Override
    public String getBotUsername() {
        return "piratoviWeatherAdviserBot";
    }
}
