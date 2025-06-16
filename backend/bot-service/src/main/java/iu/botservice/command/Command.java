package iu.botservice.command;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public abstract class Command {

    @Value("${bot.token}")
    private String botToken;

    protected TelegramClient telegramClient;

    @PostConstruct
    public void initTelegramClient() {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    public abstract void handle(Update update);

    protected abstract String getCommandName();
}
