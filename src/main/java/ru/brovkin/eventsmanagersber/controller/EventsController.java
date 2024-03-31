package ru.brovkin.eventsmanagersber.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.*;
import ru.brovkin.eventsmanagersber.service.*;
import ru.brovkin.eventsmanagersber.utils.EventSorter;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
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
        EventSorter.sortByDate(events);
        model.addAttribute("events", events);
        return "home";
    }

    @GetMapping("/previousPage")
    public String getPreviousPage(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:home";
    }

    @GetMapping("/details/{eventId}")
    public String showEventDetailsPage(@PathVariable Long eventId, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByName(authentication.getName());
            Event event = eventService.getById(eventId);
            model.addAttribute("event", event);
            setModelSubscribedAttribute(model, user, event);
            return "event";
        } catch (LuckOfDataException e) {
            return "redirect:/previousPage";
        }
    }

    private void setModelSubscribedAttribute(Model model, User user, Event event) {
        try {
            participantService.getByUserAndEvent(user, event);
            model.addAttribute("subscribed", true);
        } catch (LuckOfDataException e) {
            model.addAttribute("subscribed", false);
        }
    }

    @GetMapping("/create")
    public String showCreateEventForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        if (user.getRole().getName().equals("CREATOR")) {
            model.addAttribute(new Event());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("tags", tagService.getAllTags());
            model.addAttribute("time", "");
            return "event_redactor";
        } else {
            return "redirect:previousPage";
        }
    }

    @PostMapping("/create")
    public String createEvent(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("location") Location location,
                              @RequestParam("date") Date date,
                              @RequestParam("time") String timeString,
                              @RequestParam("tags") List<String> selectedTags) {

        Event event = getEvent(name, description, location, date, timeString, selectedTags);
        eventService.addEvent(event);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        Participant participant = new Participant();
        participant.setEvent(event);
        participant.setUser(user);
        participantService.addParticipant(participant);
        return "redirect:/event/home";
    }

    private Event getEvent(String name, String description, Location location, Date date, String timeString, List<String> selectedTags) {
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
        return event;
    }

}
