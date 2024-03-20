package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Location;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findLocationById(Long id);
    List<Location> findLocationsByCity(String city);
    List<Location> findLocationsByCityAndStreet(String city, String street);
    void deleteLocationById(Long id);
}
