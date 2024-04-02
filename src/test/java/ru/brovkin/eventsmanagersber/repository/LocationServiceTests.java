package ru.brovkin.eventsmanagersber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.Location;
import ru.brovkin.eventsmanagersber.service.LocationService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(LocationService.class)
public class LocationServiceTests {

    @Autowired
    private LocationService locationService;

    @Test
    public void testGetLocationFromDbById() {
        Location location = locationService.getById(1L);
        assertThat(location.getStreet()).isEqualTo("Чайкиной");
    }

    @Test
    public void testGetLocationsFromDbByCityAndStreet() {
        String city = "Рязань";
        String street = "Зубковой";
        List<Location> location = locationService.getLocationsByCityAndStreet(city, street);
        assertThat(location.size()).isEqualTo(1);
    }

    @Test
    public void testGetLocationsFromDbByCity() {
        String city = "Рязань";
        List<Location> location = locationService.getAllByCity(city);
        assertThat(location.size()).isEqualTo(8);
    }

    @Test
    public void testGetAllLocationsFromDb() {
        assertThat(locationService.getAllLocations().size()).isEqualTo(8);
        assertThat(locationService.getAllLocations().get(0).getStreet()).isEqualTo("Чайкиной");
    }

    @Test
    public void testUpdateLocation() {
        Location location = locationService.getById(1L);
        location.setCity("Москва");
        locationService.updateLocation(location);
        Location locationDb = locationService.getById(1L);
        assertThat(locationDb.getCity()).isEqualTo(location.getCity());
    }


    @Test
    public void testAddNewTagToDbThenDeleteById() {
        Location location = new Location();
        location.setCity("Москва");
        location.setStreet("Пл. Победы");
        location.setHouse("1");
        locationService.addLocation(location);
        Location locationDb = locationService.getById(9L);
        assertThat(locationDb.getCity()).isEqualTo(location.getCity());
        locationService.deleteLocationById(locationDb.getId());
        assertThrows(DataLackException.class, () -> locationService.getById(locationDb.getId()));
    }

    @Test
    public void testDeleteLinkedLocationById() {
        locationService.deleteLocationById(2L);
        assertThrows(DataIntegrityViolationException.class, () -> locationService.getById(1L));
    }
}
