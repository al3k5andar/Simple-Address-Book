package com.am.simpleaddressbook.repositories;

import com.am.simpleaddressbook.domain.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long>
{
    List<Contact> findByLastNameLike(String lastName);

}
