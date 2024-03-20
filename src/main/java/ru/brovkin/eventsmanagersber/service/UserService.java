package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.repository.UserRepository;

import java.util.List;

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
        if (!userRepository.findUserById(user.getId()).getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteUserById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User getUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }
}
