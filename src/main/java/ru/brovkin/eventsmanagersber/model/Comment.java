package ru.brovkin.eventsmanagersber.model;

import java.util.Date;

public class Comment {

    private long id;
    private long eventId;
    private long userId;
    private String text;
    private Date date;

    public Comment(long id, long eventId, long userId, String text, Date date) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.text = text;
        this.date = date;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
