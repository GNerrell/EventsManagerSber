package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findAll();
    Optional<Location> findLocationById(Long id);
    Optional<List<Location>> findLocationsByCity(String city);
    Optional<List<Location>> findLocationsByCityAndStreet(String city, String street);
    void deleteLocationById(Long id);
}
