package iu.botservice.handler;

import iu.botservice.command.Commands;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class UpdateRunnable implements Runnable {

    private final Update update;
    private final Commands commands;

    @Override
    public void run() {
        commands.getCommand(update.getMessage().getText()).handle(update);
    }
}
