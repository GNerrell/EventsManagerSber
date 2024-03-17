package ru.brovkin.eventsmanagersber.model;

public class Participant {

    private long id;
    private long eventId;
    private long userId;
    private boolean activeStatus;

    public Participant(long id, long eventId, long userId, boolean activeStatus) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.activeStatus = activeStatus;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}
