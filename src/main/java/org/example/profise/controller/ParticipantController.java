package org.example.profise.controller;

import org.example.profise.api.GroupApiDelegate;
import org.example.profise.api.ParticipantApiDelegate;
import org.example.profise.dto.*;
import org.example.profise.exception.NotFoundException;
import org.example.profise.service.GroupService;
import org.example.profise.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParticipantController implements ParticipantApiDelegate {

    @Autowired
    ParticipantService participantService;

    @Override
    public ResponseEntity<Void> groupGroupIdParticipantParticipantIdDelete(String groupId, String participantId) throws Exception {
        participantService.deleteParticipant(Long.parseLong(groupId), Long.parseLong(participantId));
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<String> groupGroupIdParticipantPost(String groupId, PostParticipant postParticipant) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                String.valueOf(participantService.createParticipant(Long.parseLong(groupId), postParticipant).getId())
        );
    }

}
