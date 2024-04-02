package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для реализации базовых методов CRUD для мероприятия,
 * а также дополнительных функций по поиску
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<List<User>> findAllByRole(Role role);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    void deleteUserById(Long id);
}
