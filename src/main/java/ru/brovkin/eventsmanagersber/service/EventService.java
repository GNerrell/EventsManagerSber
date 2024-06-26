package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Location;
import ru.brovkin.eventsmanagersber.repository.EventRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Реализация всех CRUD методов и дополнительных методов доступа к базе данных для Event
 */
@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public void updateEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEventById(Long id) {
        eventRepository.deleteEventById(id);
    }

    public Event getById(Long id) {
        return eventRepository.findEventById(id).orElseThrow(() -> new DataLackException("Event with id = " + id + " not found!"));
    }

    public List<Event> getEventsByName(String name) {
        return eventRepository.findAllByName(name).orElseThrow(() -> new DataLackException("No such events with name " + name + "!"));
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        return events;
    }

    public List<Event> getEventsByLocation(Location location) {
        return eventRepository.findAllByLocation(location).orElseThrow(() -> new DataLackException("No such events in this location!"));
    }

    public List<Event> getAllByDate(Date date) {
        return eventRepository.findAllByDate(date).orElseThrow(() -> new DataLackException("No such events in this date!"));
    }
}
