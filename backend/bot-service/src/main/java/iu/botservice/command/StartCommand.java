package iu.botservice.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public non-sealed class StartCommand extends Command {

    @Override
    public void handle(Update update) {
        Long chatId = update
                .getMessage()
                .getChatId();

        log.info("User {} sent command {}", chatId,
                getCommandName()
        );
        try {
            telegramClient.execute(SendMessage.builder()
                    .chatId(chatId)
                    .text("Привет")
                    .build());
        } catch (TelegramApiException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public final String getCommandName() {
        return "/start";
    }
}
