package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;
import ru.brovkin.eventsmanagersber.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    void deleteRoleById(Long id);
    List<Role> findAll();
    Optional<Role> findRoleById(Long id);
    Optional<Role> findRoleByName(String name);

}
