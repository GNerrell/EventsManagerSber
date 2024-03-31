package ru.brovkin.eventsmanagersber.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Participant;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.service.EventService;
import ru.brovkin.eventsmanagersber.service.ParticipantService;
import ru.brovkin.eventsmanagersber.service.UserService;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/event/participant")
public class ParticipantController {

    private EventService eventService;
    private UserService userService;
    private ParticipantService participantService;

    public ParticipantController(EventService eventService, UserService userService, ParticipantService participantService) {
        this.eventService = eventService;
        this.userService = userService;
        this.participantService = participantService;
    }

    @GetMapping("/add/{eventId}")
    public String get() {
        return "redirect:/event/home";
    }

    @PostMapping("/add/{eventId}")
    public String setParticipantToEvent(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByName(authentication.getName());
            Event event = eventService.getById(eventId);
            try {
                participantService.getByUserAndEvent(user, event);
                return "redirect:/event/previousPage";
            } catch (LuckOfDataException e) {
                Participant participant = new Participant();
                setParticipantAttributes(user, participant, event);
                participantService.addParticipant(participant);
                return "redirect:/event/previousPage";
            }
        } else {
            return "redirect:/event/previousPage";
        }
    }

    @Transactional
    @PostMapping("/delete/{eventId}")
    public String deleteParticipantFromEvent(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        Event event = eventService.getById(eventId);
        try {
            Participant participant = participantService.getByUserAndEvent(user, event);
            participantService.deleteById(participant.getId());
            return "redirect:/event/previousPage";
        } catch (LuckOfDataException e) {
            return "redirect:/event/previousPage";
        }
    }

    private void setParticipantAttributes(User user, Participant participant, Event event) {
        participant.setUser(user);
        participant.setEvent(event);
        participant.setActiveStatus(true);
    }


}
