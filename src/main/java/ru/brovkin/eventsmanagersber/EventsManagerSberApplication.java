package ru.brovkin.eventsmanagersber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("ru.brovkin.eventsmanagersber.repository")
public class EventsManagerSberApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsManagerSberApplication.class, args);
    }

}
