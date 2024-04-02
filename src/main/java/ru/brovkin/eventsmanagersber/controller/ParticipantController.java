package ru.brovkin.eventsmanagersber.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Participant;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.service.EventService;
import ru.brovkin.eventsmanagersber.service.ParticipantService;
import ru.brovkin.eventsmanagersber.service.UserService;

import javax.transaction.Transactional;

/**
 * Класс для управления событиями, связанными с участниками мероприятий
 */
@Controller
@RequestMapping("/event/participant")
public class ParticipantController {

    private final EventService eventService;
    private final UserService userService;
    private final ParticipantService participantService;

    public ParticipantController(EventService eventService, UserService userService, ParticipantService participantService) {
        this.eventService = eventService;
        this.userService = userService;
        this.participantService = participantService;
    }

    /**
     * Добавление пользователя в число участников мероприятия
     * @param eventId - идентификатор мероприятия, на которое записывается пользователь
     * @return возвращает на предыдущую страницу
     */
    @PostMapping("/add/{eventId}")
    public String setParticipantToEvent(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByName(authentication.getName());
            Event event = eventService.getById(eventId);
            try {
                participantService.getByUserAndEvent(user, event);
                return "redirect:/event/previousPage";
            } catch (DataLackException e) {
                Participant participant = new Participant();
                setParticipantAttributes(user, participant, event);
                participantService.addParticipant(participant);
                return "redirect:/event/previousPage";
            }
        } else {
            return "redirect:/event/previousPage";
        }
    }

    /**
     * Удаление пользователя из числа участников
     * @param eventId - идентификатор мероприятия, на которое записывается пользователь
     * @return возвращает на предыдущую страницу
     */
    @Transactional
    @PostMapping("/delete/{eventId}")
    public String deleteParticipantFromEvent(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        Event event = eventService.getById(eventId);
        if (isCreator(user)) {
            eventService.deleteEventById(eventId);
            return "redirect:/event/previousPage";
        }
        else {
            try {
                Participant participant = participantService.getByUserAndEvent(user, event);
                participantService.deleteById(participant.getId());
                return "redirect:/event/previousPage";
            } catch (DataLackException e) {
                return "redirect:/event/previousPage";
            }
        }
    }

    private boolean isCreator(User user) {
        return user.getRole().getName().equals("CREATOR") && participantService.getFirsByUser(user).getUser() == user;
    }

    private void setParticipantAttributes(User user, Participant participant, Event event) {
        participant.setUser(user);
        participant.setEvent(event);
        participant.setActiveStatus(true);
    }


}
