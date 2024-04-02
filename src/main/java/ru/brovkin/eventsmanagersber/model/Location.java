package ru.brovkin.eventsmanagersber.model;


import javax.persistence.*;
import java.util.List;

/**
 * Класс сущности места проведения
 */
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String street;
    private String house;

    @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Event> events;

    public Location() {

    }

    public Location(long id, String city, String street, String house) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Location: city " + city + '\'' +
                ", street " + street + '\'' +
                ", house " + house + '\'';
    }
}
