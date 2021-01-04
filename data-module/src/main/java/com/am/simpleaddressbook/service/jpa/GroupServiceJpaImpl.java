package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.exception.ErrorNotFoundException;
import com.am.simpleaddressbook.repositories.GroupRepository;
import com.am.simpleaddressbook.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Profile({"H2","dev"})
@Service
@Slf4j
public class GroupServiceJpaImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceJpaImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public Group save(Group group) {
        log.info("Inside Transaction !!!!");
        if(group== null){
            log.info("Given Group is NULL");
            throw new ErrorNotFoundException("Group is NULL");
        }
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
        Optional<Group> optionalGroup= groupRepository.findById(aLong);
        if(!optionalGroup.isPresent())
            throw new ErrorNotFoundException("Group ID can not be null!!!");
        return  optionalGroup.get();
    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }

    @Override
    public void deleteById(Long aLong) {
        groupRepository.deleteById(aLong);
    }
}
