package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Details;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.domain.Note;
import com.am.simpleaddressbook.exception.ErrorNotFoundException;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.DetailsService;
import com.am.simpleaddressbook.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Profile({"default","MAP"})
@Slf4j
public class ContactServiceMapImpl extends AbstractContactMap<Long, Contact> implements ContactService {

    private final NoteService noteService;
    private final DetailsService detailsService;

    public ContactServiceMapImpl(NoteService noteService, DetailsService detailsService) {
        this.noteService = noteService;
        this.detailsService = detailsService;
    }

    @Override
    public Contact save(Contact contact) {
        if(contact.getDetails()!= null)
            if(contact.getDetails().getId()== null){
                Details persistedDetails= detailsService.save(contact.getDetails());
                contact.getDetails().setId(persistedDetails.getId());
            }
        if(contact.getNote()!= null)
            if(contact.getNote().getId()== null){
                Note persistedNote= noteService.save(contact.getNote());
                contact.getNote().setId(persistedNote.getId());
            }
        return super.save(contact);
    }

    @Override
    public Contact findByGroupIdAndContactId(Long groupId, Long contactId) {
        Contact contact= super.findById(contactId);
        if(contact== null){
            log.info("We can not find Contact with ID: "+ contactId);
            throw new ErrorNotFoundException("Contact with ID: "+ contactId + " do not exists");
        }
        else {
            Group group= contact.getGroups()
                    .stream()
                    .filter(theGroup -> theGroup.getId().equals(groupId))
                    .findFirst().orElse(null);
            if(group!= null)
                return contact;
            else
                throw new ErrorNotFoundException("This Contact is not in Group");
        }
    }

    @Override
    public List<Contact> findByLastNameLikeIgnoreCase(String lastName) {
        List<Contact> contacts= new ArrayList<>();

        super.findAll().stream()
                .filter(contact -> contact.getLastName().equalsIgnoreCase(lastName))
                .map(contacts::add);
        return contacts;
    }

    @Override
    public List<Contact> findByFirstNameLikeIgnoreCase(String firstName) {
        List<Contact> contacts= new ArrayList<>();

        super.findAll().stream()
                .filter(contact -> contact.getFirstName().equalsIgnoreCase(firstName))
                .map(contacts::add);
        return contacts;
    }

    @Override
    public Set<Contact> findAll() {
        return super.findAll();
    }

    @Override
    public Contact findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public void delete(Contact contact) {
        super.delete(contact);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }
}
