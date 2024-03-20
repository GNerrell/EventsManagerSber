package ru.brovkin.eventsmanagersber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.service.RoleService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(RoleService.class)
public class RoleServiceTests {

    @Autowired
    private RoleService roleService;

    @Test
    public void testGetRoleFromDbById() {
        Role role = roleService.getById(1L);
        assertThat(role.getName()).isEqualTo("COMMON_USER");
    }

    @Test
    public void testGetRoleFromDbByName() {
        String roleName = "CREATOR";
        Role role = roleService.getByName(roleName);
        assertThat(role.getName()).isEqualTo(roleName);
    }

    @Test
    public void testAddRoleToDbThenDeleteById() {
        Role role = new Role();
        String name = "GOD";
        role.setName(name);
        roleService.addRole(role);
        Role roleDb = roleService.getByName(name);
        assertThat(roleDb.getId()).isEqualTo(role.getId());
        roleService.deleteById(roleDb.getId());
        assertThrows(LuckOfDataException.class, () -> roleService.getById(roleDb.getId()));
    }

    @Test
    public void testUpdateRole() {
        Role role = roleService.getById(1L);
        String oldName = role.getName();
        role.setName("USER");
        roleService.updateRole(role);
        assertThat(role.getName()).isEqualTo("USER");
        assertThat(role.getName()).isNotEqualTo(oldName);
    }

}
