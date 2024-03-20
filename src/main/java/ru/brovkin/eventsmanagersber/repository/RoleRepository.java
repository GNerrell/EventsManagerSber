package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    void deleteRoleById(Long id);
    Role findRoleById(Long id);
    Role findRoleByName(String name);

}
