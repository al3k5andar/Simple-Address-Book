package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.exception.ErrorNotFoundException;
import com.am.simpleaddressbook.repositories.ContactRepository;
import com.am.simpleaddressbook.repositories.GroupRepository;
import com.am.simpleaddressbook.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Profile({"H2","dev"})
@Service
@Slf4j
public class ContactServiceJpaImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final GroupRepository groupRepository;

    public ContactServiceJpaImpl(ContactRepository contactRepository, GroupRepository groupRepository) {
        this.contactRepository = contactRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Contact save(Contact contact) {
        if(contact== null){
            log.debug("Contact object can't be null");
            throw new RuntimeException("Contact object is NULL!!!");
        }
        return contactRepository.save(contact);
    }

    @Override
    public Set<Contact> findAll() {
        Set<Contact> contacts= new HashSet<>();
        contactRepository.findAll().forEach(contacts::add);
        return contacts;
    }

    @Override
    public Contact findByGroupIdAndContactId(Long groupId, Long contactId) {
        Optional<Group> optionalGroup= groupRepository.findById(groupId);
        if(!optionalGroup.isPresent()) {
            log.info("We can not find Group with ID: " + groupId);
            throw new RuntimeException("Group is NULL!!!");
        }
        else{
            Group group= optionalGroup.get();
            Contact contact= group.getContacts()
                    .stream()
                    .filter(theContact -> theContact.getId().equals(contactId))
                    .findFirst()
                    .orElse(null);
            if(contact== null){
                log.info("We can not find Contact with ID: "+ contactId);
                throw new RuntimeException("Contact is NULL!!!");
            }
            else
                return contact;
        }
    }

    @Override
    public Contact findById(Long aLong) {
        Optional<Contact> optionalContact= contactRepository.findById(aLong);
        if(optionalContact.isEmpty()){
            log.info("We can not find Contact with ID: "+ aLong);
            throw new ErrorNotFoundException("Contact with ID: "+ aLong+ " do not exists");
        }
        return optionalContact.get();
    }

    @Override
    public List<Contact> findByLastNameLike(String lastName) {
        return contactRepository.findByLastNameLike(lastName);
    }

    @Override
    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    @Override
    public void deleteById(Long aLong) {
        contactRepository.deleteById(aLong);
    }
}
