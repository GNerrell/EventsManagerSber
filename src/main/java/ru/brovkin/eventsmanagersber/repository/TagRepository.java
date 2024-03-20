package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findTagByName(String name);
    void deleteTagById(Long id);
    void deleteTagByName(String name);
}
