package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Location;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для реализации базовых методов CRUD для места проведения,
 * а также дополнительных функций по поиску
 */
public interface LocationRepository extends CrudRepository<Location, Long> {

    Optional<Location> findLocationById(Long id);
    Optional<List<Location>> findLocationsByCity(String city);
    Optional<List<Location>> findLocationsByCityAndStreet(String city, String street);
    void deleteLocationById(Long id);
}
