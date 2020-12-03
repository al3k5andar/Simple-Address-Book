package com.am.simpleaddressbook.service;

import com.am.simpleaddressbook.domain.Contact;

public interface ContactService extends BaseService<Long, Contact> {

    Contact findByGroupIdAndContactId(Long groupId, Long contactId);
}
