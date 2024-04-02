package ru.brovkin.eventsmanagersber.dto;

/**
 * Класс предназначенный для хранения значений после получения
 * шиироты и долготы из GET запроса к 2GIS API
 */

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
