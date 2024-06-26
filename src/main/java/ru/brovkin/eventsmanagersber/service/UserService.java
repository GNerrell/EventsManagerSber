package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.repository.UserRepository;

import java.util.List;

/**
 * Реализация всех CRUD методов и дополнительных методов доступа к базе данных для User
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        String userPassword = userRepository.findUserById(user.getId()).orElseThrow(() -> new DataLackException("No such user found to update!")).getPassword();
        if (!userPassword.equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteUserById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new DataLackException("User with id = " + id + " not found!"));
    }

    public User getUserByName(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new DataLackException("User with username = " + username + " not found!"));
    }

    public List<User> getAllByRole(Role role) {
        return userRepository.findAllByRole(role).orElseThrow(() -> new DataLackException("No such user with role = " + role + " !"));
    }
}
