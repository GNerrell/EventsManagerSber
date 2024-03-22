package ru.brovkin.eventsmanagersber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.Tag;
import ru.brovkin.eventsmanagersber.service.TagService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TagService.class)
public class TagServiceTests {

    @Autowired
    private TagService tagService;

    @Test
    public void testGetTagFromDbById() {
        Tag tag = tagService.getTagById(2L);
        assertThat(tag.getName()).isEqualTo("научное");
    }

    @Test
    public void testGetTagFromDbByName() {
        String name = "образовательное";
        Tag tag = tagService.getByName(name);
        assertThat(tag.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetAllLocationsFromDb() {
        assertThat(tagService.getAllTags().size()).isEqualTo(14);
        assertThat(tagService.getAllTags().get(0).getName()).isEqualTo("образовательное");
    }

    @Test
    public void testUpdateTag() {
        Tag tag = tagService.getTagById(1L);
        tag.setName("Специализированное");
        tagService.updateTag(tag);
        Tag tagDb = tagService.getTagById(1L);
        assertThat(tagDb.getName()).isEqualTo(tag.getName());
    }


    @Test
    public void testAddNewTagToDbThenDeleteById() {
        Tag tag = new Tag();
        tag.setName("для работников");
        tagService.addTag(tag);
        Tag tagDb = tagService.getByName("для работников");
        assertThat(tagDb.getName()).isEqualTo(tag.getName());
        tagService.deleteById(tagDb.getId());
        assertThrows(LuckOfDataException.class, () -> tagService.getTagById(tagDb.getId()));
    }

    @Test
    public void testDeleteLinkedTagById() {
        tagService.deleteById(1L);
        assertThrows(DataIntegrityViolationException.class, () -> tagService.getTagById(1L));
    }

}
