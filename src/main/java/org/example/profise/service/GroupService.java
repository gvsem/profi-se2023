package org.example.profise.service;

import org.example.profise.dto.PostGroup;
import org.example.profise.dto.PutGroup;
import org.example.profise.exception.NotFoundException;
import org.example.profise.model.Group;
import org.example.profise.repository.GroupRepository;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {

    @Autowired
    protected GroupRepository groupRepository;

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public Group createGroup(PostGroup dto) {
        Group group = new Group();
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        return groupRepository.save(group);
    }

    public Group putGroup(Long id, PutGroup dto) {
        var _group = getGroup(id);
        if (_group.isEmpty()) {
            throw new NotFoundException("Group not found");
        }
        var group = _group.get();
        if (dto.getName() != null) {
            group.setName(dto.getName());
        }
        if (dto.getDescription().isPresent()) {
            group.setDescription(dto.getDescription().get());
        }
        return groupRepository.save(group);
    }

    public Optional<Group> getGroup(Long id) {
        return groupRepository.findById(id);
    }

    public void deleteGroup(Long id) {
        var g = groupRepository.findById(id);
        if (g.isEmpty()) {
            throw new NotFoundException("Group not found");
        }
        try {
            groupRepository.delete(g.get());
        } catch (OptimisticEntityLockException e) {
            throw new NotFoundException("Group not found");
        }
    }

}
