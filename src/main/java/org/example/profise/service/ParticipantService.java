package org.example.profise.service;

import org.example.profise.dto.GetParticipant;
import org.example.profise.dto.PostGroup;
import org.example.profise.dto.PostParticipant;
import org.example.profise.dto.PutGroup;
import org.example.profise.exception.ConflictException;
import org.example.profise.exception.NotFoundException;
import org.example.profise.model.Group;
import org.example.profise.model.Participant;
import org.example.profise.repository.GroupRepository;
import org.example.profise.repository.ParticipantRepository;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParticipantService {

    @Autowired
    protected GroupRepository groupRepository;

    @Autowired
    protected ParticipantRepository participantRepository;

    public Participant createParticipant(Long groupId, PostParticipant dto) {
        var g = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new NotFoundException("Group");
        });
        Participant participant = new Participant();
        participant.setName(dto.getName());
        participant.setWish(dto.getWish());
        participant.setGroup(g);
        return participantRepository.save(participant);
    }

    public Participant getParticipant(Long groupId, Long participantId) {
        return participantRepository.findByGroupIdAndId(groupId, participantId).orElseThrow(() -> {
            throw new NotFoundException("Group or participant");
        });
    }

    public void deleteParticipant(Long groupId, Long id) {
        var p = getParticipant(groupId, id);
        try {
            if (p.getRecipient() != null) {
                throw new ConflictException("Can not delete user, he is already in game");
            }
            participantRepository.delete(p);
        } catch (Exception e) {
            throw new NotFoundException("Participant not found");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Participant> toss(Long groupId) {

        var g = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new NotFoundException("Group");
        });

        if (g.getParticipants().size() < 3) {
            throw new ConflictException();
        }

        for (int i = 0; i < g.getParticipants().size(); i++) {
            g.getParticipants().get(i).setRecipient(
                    g.getParticipants().get((i + 1) % g.getParticipants().size())
            );
        }

        return participantRepository.saveAll(g.getParticipants());

    }

}
