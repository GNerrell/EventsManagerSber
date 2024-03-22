package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Location;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {

    Optional<Event> findEventById(Long id);
    Optional<List<Event>> findAllByLocation(Location location);
    Optional<List<Event>> findAllByName(String name);
    Optional<List<Event>> findAllByDate(Date date);
    void deleteEventById(Long id);

}
