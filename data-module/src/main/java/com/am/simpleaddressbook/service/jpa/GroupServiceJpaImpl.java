package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.repositories.GroupRepository;
import com.am.simpleaddressbook.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Profile("JSP")
@Service
@Slf4j
public class GroupServiceJpaImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceJpaImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Set<Group> findAll() {
        Set<Group> groups= new HashSet<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    @Override
    public Group findById(Long aLong) {
        return null;
    }

    @Override
    public void delete(Group group) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
