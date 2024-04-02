package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация всех CRUD методов и дополнительных методов доступа к базе данных для Role
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteRoleById(id);
    }

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    public Role getById(Long id) {
        return roleRepository.findRoleById(id).orElseThrow(() -> new LuckOfDataException("Role with id = " + id + " not found!"));
    }
    public Role getByName(String name) {
        return roleRepository.findRoleByName(name).orElseThrow(() -> new LuckOfDataException("Role with name = " + name + " not found!"));
    }

}
