package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.model.Tag;
import ru.brovkin.eventsmanagersber.repository.TagRepository;

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

    public void deleteByName(String name) {
        tagRepository.deleteTagByName(name);
    }

    public Tag getByName(String name) {
        return tagRepository.findTagByName(name);
    }
}
