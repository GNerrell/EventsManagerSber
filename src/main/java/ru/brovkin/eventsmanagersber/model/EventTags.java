package ru.brovkin.eventsmanagersber.model;

public class EventTags {

    private long id;
    private long eventId;
    private long tagId;

    public EventTags(long id, long eventId, long tagId) {
        this.id = id;
        this.eventId = eventId;
        this.tagId = tagId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
