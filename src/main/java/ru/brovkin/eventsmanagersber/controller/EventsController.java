package ru.brovkin.eventsmanagersber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.service.EventService;

import java.util.List;

@Controller
@RequestMapping("/event")
public class EventsController {

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/home")
    public String getMainPage(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "home";
    }

    @GetMapping("/details/{eventId}")
    public String getEventDetailsPage(@PathVariable Long eventId, Model model) {
        try {
            Event event = eventService.getById(eventId);
            model.addAttribute("event", event);
            return "event";
        } catch (LuckOfDataException e) {
            return "home";
        }

    }


}
