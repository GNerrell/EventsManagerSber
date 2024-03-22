package ru.brovkin.eventsmanagersber.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.*;
import ru.brovkin.eventsmanagersber.service.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/event")
public class EventsController {

    private final EventService eventService;
    private final UserService userService;
    private final LocationService locationService;
    private final TagService tagService;
    private final ParticipantService participantService;

    public EventsController(EventService eventService, UserService userService, LocationService locationService, TagService tagService, ParticipantService participantService) {
        this.eventService = eventService;
        this.userService = userService;
        this.locationService = locationService;
        this.tagService = tagService;
        this.participantService = participantService;
    }

    @GetMapping("/home")
    public String getMainPage(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "home";
    }

    @GetMapping("/details/{eventId}")
    public String showEventDetailsPage(@PathVariable Long eventId, Model model) {
        try {
            Event event = eventService.getById(eventId);
            model.addAttribute("event", event);
            return "event";
        } catch (LuckOfDataException e) {
            return "home";
        }
    }

    @GetMapping("/create")
    public String showCreateEventForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByName(username);
            Role role = user.getRole();
            if (role.getName().equals("CREATOR")) {
                model.addAttribute(new Event());
                model.addAttribute("locations", locationService.getAllLocations());
                model.addAttribute("tags", tagService.getAllTags());
                model.addAttribute("time", "");
                return "event_redactor";
            }
        }
        return "redirect:home";
    }

    @PostMapping("/create")
    public String createEvent(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("location") Location location,
                              @RequestParam("date") Date date,
                              @RequestParam("time") String timeString,
                              @RequestParam("tags") List<String> selectedTags) {
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setLocation(location);
        event.setDate(date);

        event.setTime(Time.valueOf(timeString + ":00"));

        List<Tag> tags = selectedTags.stream()
                .map(tagService::getByName)
                .collect(Collectors.toList());
        event.setTags(tags);
        eventService.addEvent(event);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByName(username);
            Participant participant = new Participant();
            participant.setEvent(event);
            participant.setUser(user);
            participantService.addParticipant(participant);
        }
        return "redirect:/event/home";
    }

}
