package ru.brovkin.eventsmanagersber.model;

public class Location {

    private long id;
    private String city;
    private String street;
    private String house;

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

    @Override
    public String toString() {
        return "Location: city " + city + '\'' +
                ", street " + street + '\'' +
                ", house " + house + '\'';
    }
}
