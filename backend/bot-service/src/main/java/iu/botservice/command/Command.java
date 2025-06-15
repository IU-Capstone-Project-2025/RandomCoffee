package iu.botservice.command;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public sealed abstract class Command permits NoCommand, StartCommand {

    @Value("${bot.token}")
    private String botToken;
    protected TelegramClient telegramClient;

    @PostConstruct
    public void initTelegramClient() {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    public abstract void handle(Update update);

    public abstract String getCommandName();
}
