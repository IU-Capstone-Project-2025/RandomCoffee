package iu.botservice.handler;

import iu.botservice.command.Commands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@Component
public class UpdateConsumer implements LongPollingUpdateConsumer {

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final Commands commands;

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update ->
                CompletableFuture
                        .runAsync(new UpdateRunnable(update, commands), executorService)
                        .handleAsync((result, exception) -> {
                            log.error(exception.getMessage(), exception);
                            return result;
                        })
        );
    }
}
