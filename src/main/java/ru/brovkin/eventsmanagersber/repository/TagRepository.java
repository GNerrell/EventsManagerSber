package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findAll();
    Optional<Tag> findTagById(Long id);
    Optional<Tag> findTagByName(String name);
    void deleteTagById(Long id);
}
