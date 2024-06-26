package ru.brovkin.eventsmanagersber.model;

import javax.persistence.*;

/**
 * Класс сущности участника мероприятия
 */
@Entity
@Table(name = "participants", uniqueConstraints = @UniqueConstraint(columnNames={"user_id", "event_id"}))
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "subscribed")
    private boolean activeStatus;

    public Participant() {

    }

    public Participant(long id, Event event, User user, boolean activeStatus) {
        this.id = id;
        this.event = event;
        this.user = user;
        this.activeStatus = activeStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event eventId) {
        this.event = eventId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}
