package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Location;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findAllByCity(String city);
    List<Location> getLocationsByCity(String city);
    List<Location> getLocationsByCityAndStreet(String city, String street);
}
