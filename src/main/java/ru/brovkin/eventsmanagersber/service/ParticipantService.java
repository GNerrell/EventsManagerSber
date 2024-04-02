package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.exception.DataLackException;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Participant;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.repository.ParticipantRepository;

import java.util.List;

/**
 * Реализация всех CRUD методов и дополнительных методов доступа к базе данных для Participant
 */
@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public void addParticipant(Participant participant) {
        participant.setActiveStatus(true);
        participantRepository.save(participant);
    }

    public void updateParticipant(Participant participant) {
        participantRepository.save(participant);
    }

    public void deleteById(Long id) {
        participantRepository.deleteParticipantById(id);
    }

    public Participant getById(Long id) {
        return participantRepository.findParticipantById(id).orElseThrow(() -> new DataLackException("Participant with id = " + id + " not found!"));
    }

    public Participant getByUserAndEvent(User user, Event event) {
        return participantRepository.findParticipantByUserAndEvent(user, event).orElseThrow(() ->
                new DataLackException("Participant with event = " + event + "and user = " + user.getUsername() + " not found!"));
    }

    public List<Participant> getAllByActiveStatus(boolean activeStatus) {
        return participantRepository.findAllByActiveStatus(activeStatus).orElseThrow(() ->
                new DataLackException("No such participants with activeStatus = " + activeStatus + "!"));
    }

    public List<Participant> getAllByEvent(Event event) {
        return participantRepository.findAllByEvent(event).orElseThrow(() -> new DataLackException("No such participants on event = " + event.getName() + "!"));
    }

    public List<Participant> getAllByUser(User user) {
        return participantRepository.findAllByUser(user).orElseThrow(() -> new DataLackException("No such events with user = " + user.getUsername() + "!"));
    }

    public Participant getFirsByUser(User user) {
        return participantRepository.findFirstByUser(user).orElseThrow(() -> new DataLackException("There is no event creator!"));
    }

}
