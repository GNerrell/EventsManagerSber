package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Role;

import java.util.Optional;

/**
 * Интерфейс для реализации базовых методов CRUD для роли пользоваеля,
 * а также дополнительных функций по поиску
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    void deleteRoleById(Long id);
    Optional<Role> findRoleById(Long id);
    Optional<Role> findRoleByName(String name);

}
