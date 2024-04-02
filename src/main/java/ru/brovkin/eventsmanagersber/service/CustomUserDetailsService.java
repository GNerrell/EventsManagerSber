package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.repository.UserRepository;

/**
 * Класс для помощи в осуществлении авторизации пользователя
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Создает объект UserDetails для авторизации пользователя в системе
     * @param username - логин пользователя
     * @return объект класса UserDetails
     * @throws DataLackException - показывает на отсутствие данных о пользователе
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws DataLackException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new DataLackException("User not found with username: " + username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
    }
}
