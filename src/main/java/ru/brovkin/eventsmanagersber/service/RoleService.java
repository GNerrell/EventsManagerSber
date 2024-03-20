package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.repository.RoleRepository;


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

    public Role getById(Long id) {
        return roleRepository.findRoleById(id);
    }
    public Role getByName(String name) {
        return roleRepository.findRoleByName(name);
    }

}
