package ru.brovkin.eventsmanagersber.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Participant;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.service.ParticipantService;
import ru.brovkin.eventsmanagersber.service.UserService;
import ru.brovkin.eventsmanagersber.utils.EventSorter;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/event/user")
public class UserController {

    private final UserService userService;
    private final ParticipantService participantService;

    public UserController(UserService userService, ParticipantService participantService) {
        this.userService = userService;
        this.participantService = participantService;
    }


    /**
     * Форма профиля пользователя
     * @param model - для добавления данных пользователя и списка событий
     * @return переход на страницу профиля согласно роли
     */
    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        model.addAttribute("user", user);
        List<Event> userEvents = getUserEvents(user);
        if (userEvents != null) {
            EventSorter.sortByDate(userEvents);
        }
        model.addAttribute("userEvents", userEvents);
        switch (user.getRole().getName()) {
            case "CREATOR":
                return "creator_profile";
            case "COMMON_USER":
                return "common_user_profile";
            default:
                return "redirect:/event/previousPage";
        }
    }

    private List<Event> getUserEvents(User user) {
        try {
            List<Participant> participants = participantService.getAllByUser(user);
            return participants.stream()
                    .map(Participant::getEvent)
                    .collect(Collectors.toList());
        } catch (LuckOfDataException e) {
            return null;
        }
    }


}
