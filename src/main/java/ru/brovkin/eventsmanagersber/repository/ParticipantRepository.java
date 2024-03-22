package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Participant;
import ru.brovkin.eventsmanagersber.model.User;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {

    Optional<Participant> findParticipantById(Long id);
    Optional<Participant> findParticipantByUserAndEvent(User user, Event event);
    Optional<List<Participant>> findAllByActiveStatus(boolean activeStatus);
    Optional<List<Participant>> findAllByEvent(Event event);
    Optional<List<Participant>> findAllByUser(User user);
    void deleteParticipantById(Long id);
}
