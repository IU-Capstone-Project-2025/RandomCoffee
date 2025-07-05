package iu.botservice.command;

import iu.botservice.telegram.TelegramClientHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Primary
@Component
public class NoCommand implements Command {

    @Override
    public void handle(Update update) {
        Long chatId = update
                .getMessage()
                .getChatId();

        log.warn("No such command '{}' for user '{}'",
                update.getMessage().getText(),
                chatId
        );
        try {
            TelegramClientHolder.getTelegramClient().execute(SendMessage.builder()
                    .chatId(chatId)
                    .text("No such command, sorry, lad.")
                    .build());
        } catch (TelegramApiException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandName() {
        return "";
    }
}
