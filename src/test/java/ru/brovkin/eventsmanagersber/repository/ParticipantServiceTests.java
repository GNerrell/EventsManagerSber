package ru.brovkin.eventsmanagersber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.brovkin.eventsmanagersber.configuration.PasswordConfig;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Participant;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.service.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({EventService.class, UserService.class, ParticipantService.class, PasswordConfig.class})
public class ParticipantServiceTests {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private ParticipantService participantService;

    @Test
    public void testGetParticipantFromDbById() {
        Participant participant = participantService.getById(1L);
        assertThat(participant.getUser().getUsername()).isEqualTo("creator1");
    }

    @Test
    public void testGetParticipantFromDbByUserAndEvent() {
        User testUser = userService.getUserById(5L);
        Event testEvent = eventService.getById(1L);
        Participant participant = participantService.getByUserAndEvent(testUser, testEvent);
        assertThat(participant.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetParticipantsFromDbByActiveStatus() {
        boolean testStatus = true;
        List<Participant> participants = participantService.getAllByActiveStatus(testStatus);
        assertThat(participants.size()).isEqualTo(19);
    }

    @Test
    public void testGetParticipantsFromDbByEvent() {
        Event event = eventService.getById(1L);
        List<Participant> participants = participantService.getAllByEvent(event);
        assertThat(participants.size()).isEqualTo(3);
    }

    @Test
    public void testGetParticipantsFromDbByUser() {
        User user = userService.getUserById(1L);
        List<Participant> participants = participantService.getAllByUser(user);
        assertThat(participants.size()).isEqualTo(3);
    }

    @Test
    public void testUpdateParticipant() {
        Participant participant = participantService.getById(1L);
        participant.setActiveStatus(false);
        participantService.updateParticipant(participant);
        Participant participantDb = participantService.getById(1L);
        assertThat(participantDb.isActiveStatus()).isEqualTo(participant.isActiveStatus());
    }


    @Test
    public void testAddNewParticipantToDbThenDeleteById() {
        Participant participant = new Participant();
        participant.setEvent(eventService.getById(1L));
        participant.setUser(userService.getUserById(3L));
        participant.setActiveStatus(true);
        participantService.addParticipant(participant);
        Participant participantDb = participantService.getById(20L);
        assertThat(participantDb.getEvent()).isEqualTo(eventService.getById(1L));
        assertThat(participantDb.getUser()).isEqualTo(userService.getUserById(3L));
        participantService.deleteById(participantDb.getId());
        assertThrows(DataLackException.class, () -> participantService.getById(participantDb.getId()));
    }

    @Test
    public void testDeleteLinkedParticipantById() {
        participantService.deleteById(1L);
        assertThrows(DataLackException.class, () -> participantService.getById(1L));
    }
}
