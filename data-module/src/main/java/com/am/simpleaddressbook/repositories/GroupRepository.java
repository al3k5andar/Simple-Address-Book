package com.am.simpleaddressbook.repositories;

import com.am.simpleaddressbook.domain.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByName(String name);
}
