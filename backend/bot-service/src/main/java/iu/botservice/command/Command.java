package iu.botservice.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    void handle(Update update);

    String getCommandName();
}
