package iu.botservice.telegram;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public final class TelegramClientHolder {

    @Value("${bot.token}")
    private String botToken;

    @Getter
    private static TelegramClient telegramClient;

    @PostConstruct
    public void initTelegramClient() {
        telegramClient = new OkHttpTelegramClient(botToken);
    }
}
