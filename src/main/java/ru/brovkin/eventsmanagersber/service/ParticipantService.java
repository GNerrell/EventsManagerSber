package ru.brovkin.eventsmanagersber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.model.Event;
import ru.brovkin.eventsmanagersber.model.Participant;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.repository.ParticipantRepository;

import java.util.List;

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

    public Participant getByUserAndEvent(User user, Event event) {
        return participantRepository.findParticipantByUserAndEvent(user, event);
    }

    public List<Participant> getAllByActiveStatus(boolean activeStatus) {
        return participantRepository.findAllByActiveStatus(activeStatus);
    }

    public List<Participant> getAllByEvent(Event event) {
        return participantRepository.findAllByEvent(event);
    }

    public List<Participant> getAllByUser(User user) {
        return participantRepository.findAllByUser(user);
    }
}
