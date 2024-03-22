package ru.brovkin.eventsmanagersber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Location;
import ru.brovkin.eventsmanagersber.model.Tag;
import ru.brovkin.eventsmanagersber.service.EventService;
import ru.brovkin.eventsmanagersber.service.LocationService;
import ru.brovkin.eventsmanagersber.service.TagService;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({EventService.class, LocationService.class, TagService.class})
public class EventServiceTests {

    @Autowired
    private EventService eventService;

    @Autowired
    private LocationService locationService;

    @Autowired TagService tagService;

    @Test
    public void testGetEventFromDbById() {
        Event event = eventService.getById(1L);
        assertThat(event.getName()).isEqualTo("Выставка «100 лет тому назад»");
    }

    @Test
    public void testGetEventsFromDbByLocation() {
        Location location = locationService.getById(6L);
        List<Event> events = eventService.getEventsByLocation(location);
        assertThat(events.size()).isEqualTo(2);
    }

    @Test
    public void testGetEventsFromDbByName() {
        String name = "Выставка «100 лет тому назад»";
        List<Event> event = eventService.getEventsByName(name);
        assertThat(event.size()).isEqualTo(2);
    }

    @Test
    public void testGetEventFromDbByDate() {
        Date date = Date.valueOf("2024-04-05");
        List<Event> events = eventService.getAllByDate(date);
        assertThat(events.size()).isEqualTo(2);
    }

    @Test
    public void testUpdateEvent() {
        Event event = eventService.getById(1L);
        event.setDescription("Нет описания");
        eventService.updateEvent(event);
        Event eventDb = eventService.getById(1L);
        assertThat(eventDb.getDescription()).isEqualTo(event.getDescription());
    }


    @Test
    public void testAddNewEventToDbThenDeleteById() {
        Event event = new Event();
        event.setName("Утренник");
        event.setDescription("Описание");
        event.setDate(new java.util.Date());
        event.setLocation(locationService.getById(3L));
        event.setTime(Time.valueOf("12:00:00"));

        List<Tag> tags = new ArrayList<>();
        tags.add(tagService.getTagById(1L));
        tags.add(tagService.getTagById(2L));
        tags.add(tagService.getTagById(3L));

        event.setTags(tags);
        eventService.addEvent(event);
        Event eventDb = eventService.getById(7L);
        assertThat(eventDb.getDescription()).isEqualTo(event.getDescription());
        assertThat(eventDb.getDate()).isEqualTo(event.getDate());
        eventService.deleteEventById(eventDb.getId());
        assertThrows(LuckOfDataException.class, () -> eventService.getById(eventDb.getId()));
    }

    @Test
    public void testDeleteLinkedEventById() {
        eventService.deleteEventById(1L);
        assertThrows(LuckOfDataException.class, () -> eventService.getById(1L));
    }
}
