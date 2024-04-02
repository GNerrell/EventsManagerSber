package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.Location;
import ru.brovkin.eventsmanagersber.repository.LocationRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация всех CRUD методов и дополнительных методов доступа к базе данных для Location
 */
@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void addLocation(Location location) {
        locationRepository.save(location);
    }

    public void updateLocation(Location location) {
        locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        locationRepository.findAll().forEach(locations::add);
        return locations;
    }

    public void deleteLocationById(Long id) {
        locationRepository.deleteLocationById(id);
    }

    public Location getById(Long id) {
        return locationRepository.findLocationById(id).orElseThrow(() -> new DataLackException("Location with id = " + id + " not found!"));
    }

    public List<Location> getAllByCity(String city) {
        return locationRepository.findLocationsByCity(city).orElseThrow(() -> new DataLackException("No such location with city = " + city + " !"));
    }

    public List<Location> getLocationsByCityAndStreet(String city, String street){
        return locationRepository.findLocationsByCityAndStreet(city, street).orElseThrow(() -> new DataLackException("No such location in database!"));
    }

}
