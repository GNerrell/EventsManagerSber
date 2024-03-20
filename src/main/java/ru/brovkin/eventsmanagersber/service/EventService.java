package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Location;
import ru.brovkin.eventsmanagersber.repository.EventRepository;

import java.util.Date;
import java.util.List;

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

    public void deleteEventsByName(String name) {
        eventRepository.deleteEventsByName(name);
    }

    void deleteEventByNameAndDate(String name, Date date) {
        eventRepository.deleteEventByNameAndDate(name, date);
    }

    public List<Event> getEventsByLocation(Location location) {
        return eventRepository.findAllByLocation(location);
    }

    public Event getByName(String name) {
        return eventRepository.findByName(name);
    }

    public List<Event> getAllByDate(Date date) {
        return eventRepository.findAllByDate(date);
    }
}
