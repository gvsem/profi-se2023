package org.example.profise.controller;

import org.example.profise.api.GroupApiDelegate;
import org.example.profise.dto.*;
import org.example.profise.exception.NotFoundException;
import org.example.profise.model.Group;
import org.example.profise.model.Participant;
import org.example.profise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupController implements GroupApiDelegate {

    public static List<GetParticipant> participantsToJson(List<Participant> participants) {
        return participants.stream().map((p) -> {
            return new GetParticipant()
                    .id(String.valueOf(p.getId()))
                    .name(p.getName())
                    .wish(p.getWish())
                    .recipient(
                            p.getRecipient() == null ?
                                    null :
                                    new GetParticipant()
                                            .id(String.valueOf(p.getRecipient().getId()))
                                            .name(p.getRecipient().getName())
                                            .wish(p.getRecipient().getWish())
                    );
        }).collect(Collectors.toList());
    }


    public static ResponseEntity<GetGroupFull> groupsToJson(Group g) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new GetGroupFull()
                        .id(String.valueOf(g.getId()))
                        .name(g.getName())
                        .description(g.getDescription())
                        .participants(participantsToJson(g.getParticipants())
                        )
        );
    }

    @Autowired
    GroupService groupService;

    @Override
    public ResponseEntity<Void> groupGroupIdDelete(String groupId) throws Exception {
        groupService.deleteGroup(Long.parseLong(groupId));
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<GetGroupFull> groupGroupIdGet(String groupId) throws Exception {

        var g = groupService.getGroup(Long.parseLong(groupId)).orElseThrow(() -> {
            throw new NotFoundException();
        });

       return groupsToJson(g);
    }

    @Override
    public ResponseEntity<Void> groupGroupIdPut(String groupId, PutGroup putGroup) throws Exception {
        groupService.putGroup(Long.parseLong(groupId), putGroup);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Override
    public ResponseEntity<String> groupPost(PostGroup postGroup) throws Exception {
        var g = groupService.createGroup(postGroup);
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(g.getId()));
    }

    @Override
    public ResponseEntity<List<GetGroup>> groupsGet() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                groupService.getGroups().stream().map((g) -> {
                    return new GetGroup()
                            .id(String.valueOf(g.getId()))
                            .name(g.getName())
                            .description(g.getDescription());
                }).collect(Collectors.toList())
        );
    }
}
