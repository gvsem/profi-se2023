package org.example.profise.controller;

import org.example.profise.api.GameApiDelegate;
import org.example.profise.dto.GetParticipant;
import org.example.profise.exception.NotFoundException;
import org.example.profise.service.GroupService;
import org.example.profise.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameController implements GameApiDelegate {

    @Autowired
    GroupService groupService;

    @Autowired
    ParticipantService participantService;

    @Override
    public ResponseEntity<GetParticipant> groupGroupIdParticipantParticipantIdRecipientGet(String groupId, String participantId) throws Exception {

        var p = participantService.getParticipant(Long.parseLong(groupId), Long.parseLong(participantId));
        if (p.getRecipient() == null) {
            throw new NotFoundException("No recipient");
        }

        return ResponseEntity.status(HttpStatus.OK).body(new GetParticipant()
                .id(String.valueOf(p.getRecipient().getId()))
                .name(p.getRecipient().getName())
                .wish(p.getRecipient().getWish()));

    }

    @Override
    public ResponseEntity<List<GetParticipant>> groupGroupIdTossPost(String groupId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                GroupController.participantsToJson(
                participantService.toss(Long.parseLong(groupId))
        ));
    }

}
