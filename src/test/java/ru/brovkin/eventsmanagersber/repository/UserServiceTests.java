package ru.brovkin.eventsmanagersber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.brovkin.eventsmanagersber.configuration.PasswordConfig;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.service.RoleService;
import ru.brovkin.eventsmanagersber.service.UserService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserService.class, RoleService.class, PasswordConfig.class})
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    public void testGetUserFromDbById() {
        User user = userService.getUserById(1L);
        assertThat(user.getUsername()).isEqualTo("user1");
    }

    @Test
    public void testGetUserFromDbByUsername() {
        String username = "user1";
        User user = userService.getUserByName(username);
        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetAllUsersFromDbByRole() {
        Role role = roleService.getById(1L);
        List<User> users = userService.getAllByRole(role);
        assertThat(users.size()).isEqualTo(4);
    }

    @Test
    public void testUpdateUser() {
        User user = userService.getUserById(1L);
        user.setUsername("USER");
        userService.updateUser(user);
        User userDb = userService.getUserById(1L);
        assertThat(userDb.getUsername()).isEqualTo(user.getUsername());
    }


    @Test
    public void testAddNewUserToDbThenDeleteById() {
        User user = new User();
        user.setUsername("SuperAndrey");
        user.setPassword("123");
        long roleId = 1;
        user.setRole(roleService.getById(roleId));
        user.setFirstName("Андрей");
        user.setLastName("Сидоров");
        userService.addUser(user);
        User userDb = userService.getUserByName("SuperAndrey");
        assertThat(userDb.getUsername()).isEqualTo(user.getUsername());
        userService.deleteById(userDb.getId());
        assertThrows(LuckOfDataException.class, () -> userService.getUserById(userDb.getId()));
    }

    @Test
    public void testDeleteLinkedUserById() {
        userService.deleteById(1L);
        assertThrows(LuckOfDataException.class, () -> userService.getUserById(1L));
    }
}
