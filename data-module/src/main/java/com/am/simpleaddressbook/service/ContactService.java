package com.am.simpleaddressbook.service;

import com.am.simpleaddressbook.domain.Contact;

import java.util.List;

public interface ContactService extends BaseService<Long, Contact> {

    Contact findByGroupIdAndContactId(Long groupId, Long contactId);

    List<Contact> findByLastNameLikeIgnoreCase(String lastName);

    List<Contact> findByFirstNameLikeIgnoreCase(String firstName);
}
