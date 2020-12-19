package com.am.simpleaddressbook.service;

import com.am.simpleaddressbook.domain.Contact;

import java.util.List;

public interface ContactService extends BaseService<Long, Contact> {

    Contact findByGroupIdAndContactId(Long groupId, Long contactId);

    List<Contact> findByLastNameLike(String lastName);
}
