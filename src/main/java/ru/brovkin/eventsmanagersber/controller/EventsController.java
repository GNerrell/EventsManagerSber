package ru.brovkin.eventsmanagersber.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.brovkin.eventsmanagersber.dto.CoordinatesDTO;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.*;
import ru.brovkin.eventsmanagersber.service.*;
import ru.brovkin.eventsmanagersber.utils.EventSorter;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для управления соытиями, связанныими с мероприятими
 */
@Controller
@RequestMapping("/event")
public class EventsController {

    private final EventService eventService;
    private final UserService userService;
    private final LocationService locationService;
    private final TagService tagService;
    private final ParticipantService participantService;
    private final GeocoderApi2GisService geocoderApi2GisService;

    public EventsController(EventService eventService, UserService userService, LocationService locationService, TagService tagService, ParticipantService participantService, GeocoderApi2GisService geocoderApi2GisService) {
        this.eventService = eventService;
        this.userService = userService;
        this.locationService = locationService;
        this.tagService = tagService;
        this.participantService = participantService;
        this.geocoderApi2GisService = geocoderApi2GisService;
    }

    /**
     *
     * @param model - доставить на html страницу события
     * @return Домашняя страница
     */
    @GetMapping("/home")
    public String getMainPage(Model model) {
        List<Event> events = eventService.getAllEvents();
        EventSorter.sortByDate(events);
        model.addAttribute("events", events);
        return "home";
    }

    /**
     *
     * @param request - требуется для получения URL предыдущей страницы, с которой пользователь перешел на текущую страницу.
     * @return возврат просиходит либо на предыдущую страницу либо на домашнюю
     */
    @GetMapping("/previousPage")
    public String getPreviousPage(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:home";
    }

    /**
     * Просмотр события по его идентификатору
     * @param eventId - идентификатор события, которое желаем просмотреть
     * @param model - с её помощью передаем объекты события на форму
     * @return переносит на форму с отдельным событием,
     * либо на предыдщую страницу, если событие не найдено в базе данных
     */
    @GetMapping("/details/{eventId}")
    public String showEventDetailsPage(@PathVariable Long eventId, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByName(authentication.getName());
            Event event = eventService.getById(eventId);
            model.addAttribute("event", event);
            setModelSubscribedAttribute(model, user, event);
            return "event";
        } catch (DataLackException e) {
            return "redirect:/previousPage";
        }
    }

    /**
     * Форма создания события
     * @param model - с ее помощью на форму переносятся все тэги, локации и времени
     * @return переносит на форму создания мероприятия,
     * либо на предыдущую страницу, если пользователь не имеет роли CREATOR
     */
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


    /**
     * Создание нового мероприятия по заполненным полям
     * @param name - поле класса Event
     * @param description - поле класса Event
     * @param location - поле класса Event
     * @param date - поле класса Event
     * @param timeString - поле класса Event
     * @param selectedTags - поле класса Event
     * @return возвращает на домашнюю страницу после создания
     */
    @PostMapping("/create")
    public String createEvent(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("location") Location location,
                              @RequestParam("date") Date date,
                              @RequestParam("time") String timeString,
                              @RequestParam("tags") List<String> selectedTags) {

        Event event = setEvent(name, description, location, date, timeString, selectedTags);
        eventService.addEvent(event);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        Participant participant = new Participant();
        participant.setEvent(event);
        participant.setUser(user);
        participantService.addParticipant(participant);
        return "redirect:/event/home";
    }

    /**
     * Отображает карту с маршрутом от пользователя до мероприятия
     * @param eventId - id мероприятия
     * @param city - город пользователя
     * @param street - улица пользователя
     * @param house - дом пользователя
     * @param wayToMove - способ передвижения (пешком или на автомобиле)
     * @param model - требуется для передачи всех описанных выше значений на форму
     * @return форма с картой 2GIS
     */
    @GetMapping("/map/{eventId}")
    public String showInteractiveMap(@PathVariable Long eventId,
                                     @RequestParam("city") String city,
                                     @RequestParam("street") String street,
                                     @RequestParam("house") String house,
                                     @RequestParam("way") String wayToMove,
                                     Model model) {
        Event event = eventService.getById(eventId);
        model.addAttribute("event", event);
        model.addAttribute("way", wayToMove);
        Location location = event.getLocation();
        CoordinatesDTO coordinatesEvent = geocoderApi2GisService.getCoordinatesFromLocationAddress(location);
        System.out.println(coordinatesEvent.getLat());
        System.out.println(coordinatesEvent.getLon());
        CoordinatesDTO coordinatesUser = geocoderApi2GisService.getCoordinatesFromLocationAddress(city, street, house);
        model.addAttribute("coordinatesEvent", coordinatesEvent);
        model.addAttribute("coordinatesUser", coordinatesUser);
        return "map_2gis";
    }

    private void setModelSubscribedAttribute(Model model, User user, Event event) {
        try {
            participantService.getByUserAndEvent(user, event);
            model.addAttribute("subscribed", true);
        } catch (DataLackException e) {
            model.addAttribute("subscribed", false);
        }
    }

    private Event setEvent(String name, String description, Location location, Date date, String timeString, List<String> selectedTags) {
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
