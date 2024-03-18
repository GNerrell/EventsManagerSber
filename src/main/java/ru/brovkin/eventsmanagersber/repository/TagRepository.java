package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag getTagByName(String name);
}
