package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Address;
import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Details;
import com.am.simpleaddressbook.domain.Note;
import com.am.simpleaddressbook.repositories.ContactRepository;
import com.am.simpleaddressbook.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Profile("JPA")
@Service
@Slf4j
public class ContactServiceJpaImpl implements ContactService {
    private final ContactRepository contactRepository;

    public ContactServiceJpaImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact save(Contact contact) {
        if(contact== null){
            log.debug("Contact object can't be null");
            return new Contact();
        }
        else{
            Optional<Contact> optionalContact= contactRepository.findById(contact.getId());
            if(optionalContact.isPresent()){
                Contact theContact= optionalContact.get();
                if(theContact.getNote()!= null){
                    Note note= theContact.getNote();
                    note.setDescription(contact.getNote().getDescription());
                }
                else
                    theContact.setNote(new Note());
                if(theContact.getDetails()!= null){
                    Details details= theContact.getDetails();
                    if(details.getAddress()!= null){
                        Address address= details.getAddress();
                        address.setCountry(contact.getDetails().getAddress().getCountry());
                        address.setCity(contact.getDetails().getAddress().getCity());
                        address.setStreet(contact.getDetails().getAddress().getStreet());
                        address.setHouseNo(contact.getDetails().getAddress().getHouseNo());
                    }
                    else
                        details.setAddress(new Address());

                    details.setId(contact.getDetails().getId());
                    details.setBirthday(contact.getDetails().getBirthday());
                    details.setNickName(contact.getDetails().getNickName());
                    details.setPhone(contact.getDetails().getPhone());
                    details.setWorkPhone(contact.getDetails().getWorkPhone());
                    details.setEmail(contact.getDetails().getEmail());
                }
                else
                    theContact.setDetails(new Details());

                if (theContact.getGroups()!= null && theContact.getGroups().size()>0){
                    theContact.getGroups().retainAll(contact.getGroups());
                }
                theContact.setId(contact.getId());
                theContact.setContactType(contact.getContactType());
                theContact.setImage(contact.getImage());
                theContact.setMiddleName(contact.getMiddleName());
                theContact.setLastName(contact.getLastName());
                theContact.setFirstName(contact.getFirstName());
                contactRepository.save(theContact);
            }
            return contactRepository.save(contact);
        }
    }

    @Override
    public Set<Contact> findAll() {
        return null;
    }

    @Override
    public Contact findById(Long aLong) {
        return null;
    }

    @Override
    public void delete(Contact contact) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
