package iu.botservice.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnBooleanProperty(name = "scheduler.enabled")
@Component
public class SurveyScheduler {

    @Scheduled(cron = "${scheduler.time.survey}")
    public void schedule() {
        log.info("SurveyScheduler is running");
    }
}
