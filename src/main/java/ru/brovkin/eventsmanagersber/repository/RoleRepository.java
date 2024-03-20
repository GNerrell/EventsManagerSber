package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    void deleteRoleById(Long id);
    Optional<Role> findRoleById(Long id);
    Optional<Role> findRoleByName(String name);

}
