package iu.botservice.handler;

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
    private final UpdateRunnable updateRunnable;

    // TODO monitor thread-safe implementation (like singleton)
    // TODO threads aren't auto deleted
    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update ->
                CompletableFuture
                        .runAsync(updateRunnable.setUpdateAndGet(update), executorService)
                        .handleAsync((result, exception) -> {
                            log.error(exception.getMessage(), exception);
                            return result;
                        })
        );
    }
}
