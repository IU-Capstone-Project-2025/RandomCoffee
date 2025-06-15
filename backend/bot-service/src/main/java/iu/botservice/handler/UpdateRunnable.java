package iu.botservice.handler;

import iu.botservice.command.Commands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Component
public class UpdateRunnable implements Runnable {

    private Update update;
    private final Commands commands;

    public UpdateRunnable setUpdateAndGet(Update update) {
        this.update = update;
        return this;
    }

    @Override
    public void run() {
        commands.getCommand(update.getMessage().getText()).handle(update);
    }
}
