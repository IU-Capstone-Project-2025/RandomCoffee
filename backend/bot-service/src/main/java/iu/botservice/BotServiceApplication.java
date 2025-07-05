package iu.botservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BotServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotServiceApplication.class, args);
    }
}
