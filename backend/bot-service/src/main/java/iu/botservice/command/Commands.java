package iu.botservice.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public final class Commands {

    private final List<Command> commands;
    private final Command defaultCommand;

    public Command getCommand(String commandName) {
        return commands.stream()
                .filter(command -> command.getCommandName().equals(commandName))
                .findFirst()
                .orElse(defaultCommand);
    }
}
