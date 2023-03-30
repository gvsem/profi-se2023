package org.example.profise.repository;

import org.example.profise.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.profise.model.Participant;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    void deleteByGroupAndId(Group group, Long id);

    Optional<Participant> findByGroupIdAndId(Long group_id, Long id);

}
