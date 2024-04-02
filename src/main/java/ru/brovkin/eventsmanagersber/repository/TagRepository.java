package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Tag;

import java.util.Optional;

/**
 * Интерфейс для реализации базовых методов CRUD для категории мероприятия,
 * а также дополнительных функций по поиску
 */
public interface TagRepository extends CrudRepository<Tag, Long> {

    Optional<Tag> findTagById(Long id);
    Optional<Tag> findTagByName(String name);
    void deleteTagById(Long id);
}
