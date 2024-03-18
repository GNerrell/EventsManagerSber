package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAllByRole(Role role);
    User findByUsername(String username);
}
