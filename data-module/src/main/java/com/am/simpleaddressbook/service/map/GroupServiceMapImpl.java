package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default","map"})
public class GroupServiceMapImpl extends AbstractContactMap<Long, Group> implements GroupService {

    private final ContactService contactService;

    public GroupServiceMapImpl(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public Group save(Group group) {
        if(group.getContacts()!= null && group.getContacts().size()> 0)
            group.getContacts().forEach(contact -> {
                if(contact.getId()== null){
                    Contact persistedContact= contactService.save(contact);
                    contact.setId(persistedContact.getId());
                }
            });
        return super.save(group);
    }

    @Override
    public Set<Group> findAll() {
        return super.findAll();
    }

    @Override
    public Group findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public void delete(Group group) {
        super.delete(group);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }
}
