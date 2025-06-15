package iu.botservice.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public final class Commands {

    private final ApplicationContext applicationContext;
    private final List<Command> commands;

    public Command getCommand(String commandName) {
        return commands.stream()
                .filter(command -> command.getCommandName().equals(commandName))
                .findFirst()
                .orElse(applicationContext.getBean(NoCommand.class));
    }
}
