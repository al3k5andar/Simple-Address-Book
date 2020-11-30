package com.am.simpleaddressbook.repositories;

import com.am.simpleaddressbook.domain.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact, Long>
{
    Optional<Contact> findByLastName(String lastName);
}
