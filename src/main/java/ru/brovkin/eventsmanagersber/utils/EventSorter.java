package ru.brovkin.eventsmanagersber.utils;

import ru.brovkin.eventsmanagersber.model.Event;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventSorter {
    public static void sortByDate(List<Event> events) {
        Comparator<Event> byDate = Comparator.comparing(Event::getDate);
        events.sort(byDate);
    }
}
