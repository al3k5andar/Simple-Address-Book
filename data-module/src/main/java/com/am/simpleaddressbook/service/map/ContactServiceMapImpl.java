package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Details;
import com.am.simpleaddressbook.domain.Note;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.DetailsService;
import com.am.simpleaddressbook.service.NoteService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
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
