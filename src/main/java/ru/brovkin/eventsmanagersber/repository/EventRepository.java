package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Location;
import ru.brovkin.eventsmanagersber.model.Tag;

import java.util.Date;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findAllByLocation(Location location);
    List<Event> findAllByTags(List<Tag> tags);
    Event findByName(String name);
    List<Event> findAllByDate(Date date);
}
