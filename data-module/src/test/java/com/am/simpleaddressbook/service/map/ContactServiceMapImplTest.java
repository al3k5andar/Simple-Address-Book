package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Set;

class ContactServiceMapImplTest {
    ContactServiceMapImpl contactServiceMap;

    Contact contact;

    Long id= 1L;
    String firstName= "Alex";
    String lastName= "Peters";
    String middleName= "Pit";
    Details details= new Details();
    Note note= new Note();
    ContactType contactType= ContactType.STANDARD;

    @BeforeEach
    void setUp() {
        contactServiceMap= new ContactServiceMapImpl(new NoteServiceMapImpl(),
                new DetailsServiceMapImpl(new AddressServiceMapImpl()));

        contact= new Contact();
    }

    @Test
    void save() {
//        Given
        contact.setId(id);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setMiddleName(middleName);
        contact.setDetails(details);
        contact.setNote(note);
        contact.setContactType(contactType);

//        When
        Contact savedContact= contactServiceMap.save(contact);

//        Then
        Assertions.assertNotNull(savedContact);
        Assertions.assertEquals(id, savedContact.getId());
        Assertions.assertEquals(firstName, savedContact.getFirstName());
        Assertions.assertEquals(lastName, savedContact.getLastName());
        Assertions.assertEquals(middleName, savedContact.getMiddleName());
        Assertions.assertEquals(1, savedContact.getDetails().getId());
        Assertions.assertEquals(1, savedContact.getNote().getId());
        Assertions.assertEquals(contactType, savedContact.getContactType());
    }

    @Test
    void findAll() {
//        Given
        Contact contact1= new Contact();
        Contact contact2= new Contact();

        contactServiceMap.save(contact1);
        contactServiceMap.save(contact2);

//        When
        Set<Contact> contacts= contactServiceMap.findAll();

//        Then
        Assertions.assertEquals(2, contacts.size());
    }

    @Test
    void findById() {
//        Given
        contact.setId(id);
        contactServiceMap.save(contact);

//        When
        Contact theContact= contactServiceMap.findById(id);

//        Then
        Assertions.assertEquals(id, theContact.getId());
    }

    @Test
    void delete() {
//        Given
        contact.setId(id);
        contactServiceMap.save(contact);

//        When
        contactServiceMap.delete(contact);

//        Then
        Assertions.assertEquals(0, contactServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
//        Given
        contact.setId(id);
        contactServiceMap.save(contact);

//        When
        contactServiceMap.deleteById(id);

//        Then
        Assertions.assertEquals(0, contactServiceMap.findAll().size());
    }

    @Test
    void findByGroupIdAndContactId() {
//        Given
        Group group= new Group();
        group.setId(id);
        contact.setId(id);
        contact.getGroups().add(group);
        contactServiceMap.save(contact);


//        When
        Contact searchedContact= contactServiceMap.findByGroupIdAndContactId(1L,1L);

//        Then
        Assertions.assertNotNull(searchedContact);
        Assertions.assertEquals(1L, searchedContact.getId());
    }

    @Test
    void testFindByGroupIdAndContactIdWhenNonExistsContactId(){
//        Given
        contact.setId(2L);
        contact.getGroups().add(new Group());
        String expectedMessage= "Contact with ID: 1 do not exists";

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                contactServiceMap.findByGroupIdAndContactId(1L, 1L);
            }
        });

//        Then
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testFindByGroupIdAndContactIdWhenNonExistsGroupId(){
//        Given
        contact.setId(id);
        contactServiceMap.save(contact);
        String expectedMessage= "This Contact is not in Group";

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                contactServiceMap.findByGroupIdAndContactId(2L, id);
            }
        });

//        Then
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}