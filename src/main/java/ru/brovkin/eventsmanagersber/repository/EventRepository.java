package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Location;

import java.util.Date;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findAllByLocation(Location location);
    Event findByName(String name);
    List<Event> findAllByDate(Date date);
    void deleteEventsByName(String name);
    void deleteEventByNameAndDate(String name, Date date);
    void deleteEventById(Long id);

}
