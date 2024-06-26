package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.Tag;
import ru.brovkin.eventsmanagersber.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация всех CRUD методов и дополнительных методов доступа к базе данных для Tag
 */
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void addTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void updateTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void deleteById(Long id) {
        tagRepository.deleteTagById(id);
    }

    public Tag getTagById(Long id) {
        return tagRepository.findTagById(id).orElseThrow(() -> new DataLackException("Tag with id = " + id + " not found!"));
    }

    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        tagRepository.findAll().forEach(tags::add);
        return tags;
    }

    public Tag getByName(String name) {
        return tagRepository.findTagByName(name).orElseThrow(() -> new DataLackException("Tag with name = " + name + " not found!"));
    }
}
