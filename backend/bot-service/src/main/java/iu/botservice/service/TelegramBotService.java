package iu.botservice.service;

import iu.botservice.handler.UpdateConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@RequiredArgsConstructor
@Service
public class TelegramBotService implements SpringLongPollingBot {

    @Value("${bot.token}")
    private String botToken;

    private final UpdateConsumer updateConsumer;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}
