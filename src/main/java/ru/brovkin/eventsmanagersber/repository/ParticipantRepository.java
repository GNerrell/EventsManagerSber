package ru.brovkin.eventsmanagersber.repository;

import org.springframework.data.repository.CrudRepository;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Participant;
import ru.brovkin.eventsmanagersber.model.User;

import java.util.List;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {

    Participant findByUserAndEvent(User user, Event event);
    List<Participant> findAllByActiveStatus(boolean activeStatus);
    List<Participant> findAllByEvent(Event event);
    List<Participant> findAllByUser(User user);
}
