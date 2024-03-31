package ru.brovkin.eventsmanagersber.DTO;

public class CoordinatesDTO {
    private final Double lat;
    private final Double lon;

    public CoordinatesDTO(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
