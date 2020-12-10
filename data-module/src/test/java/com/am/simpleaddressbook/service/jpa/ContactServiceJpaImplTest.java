package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.repositories.ContactRepository;
import com.am.simpleaddressbook.repositories.GroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class ContactServiceJpaImplTest {

    @Mock
    ContactRepository contactRepository;

    @Mock
    GroupRepository groupRepository;

    ContactServiceJpaImpl serviceJpa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceJpa= new ContactServiceJpaImpl(contactRepository, groupRepository);
    }

    @Test
    void testSaveGivenNull(){
//        Given
        String expectedMessage= "Contact object is NULL!!!";

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                serviceJpa.save(null);
            }
        });

//        Then
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void save() {
//        Given
        Contact contact= new Contact();
        contact.setId(1L);
        Mockito.when(contactRepository.save(Mockito.any())).thenReturn(contact);

//        When
        Contact savedContact= serviceJpa.save(contact);

//        Then
        Assertions.assertNotNull(savedContact);
        Assertions.assertEquals(1L, savedContact.getId());
    }

    @Test
    void findAll() {
//        Given
        Contact contact1= new Contact();
        Contact contact2= new Contact();
        Set<Contact> contacts= new HashSet<>();
        contacts.add(contact1);
        contacts.add(contact2);

        Mockito.when(contactRepository.findAll()).thenReturn(contacts);

//        When
        Set<Contact> baseContacts= serviceJpa.findAll();

//        Then
        Assertions.assertEquals(2L, baseContacts.size());
    }

    @Test
    void testFindByGroupIdAndContactIdWhenGroupNonExist(){
//        Give
        String expectedMessage= "Group is NULL!!!";
        Mockito.when(groupRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                serviceJpa.findByGroupIdAndContactId(1L, 2L);
            }
        });

//        Then
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testFindByGroupIdAndContactIdWhenContactNonExist(){
//        Given
        Group group= new Group();
        Contact contact= new Contact();
        contact.setId(3L);
        group.getContacts().add(contact);
        String expectedMessage= "Contact is NULL!!!";
        Mockito.when(groupRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(group));

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                serviceJpa.findByGroupIdAndContactId(1L, 2L);
            }
        });

//        Then
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void findByGroupIdAndContactId() {
//        Given
        Group group= new Group();
        Contact contact= new Contact();
        contact.setId(2L);
        group.getContacts().add(contact);

        Mockito.when(groupRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(group));

//        When
        Contact searchedContact= serviceJpa.findByGroupIdAndContactId(1L, 2L);

//        Then
        Assertions.assertNotNull(searchedContact);
        Assertions.assertEquals(2L, searchedContact.getId());
    }

    @Test
    void testFindByIdGivenNonExistId(){
//        Given
        String expectedMessage= "Contact with ID: 1 do not exists";

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                serviceJpa.findById(1L);
            }
        });

//        Then
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void findById() {
//        Given
        Contact contact= new Contact();
        contact.setId(1L);
        Mockito.when(contactRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(contact));

//        When
        Contact searchedContact= serviceJpa.findById(1L);

//        Then
        Assertions.assertNotNull(searchedContact);
        Assertions.assertEquals(1L, searchedContact.getId());
    }

    @Test
    void delete() {
//        Give
        Contact contact= new Contact();

//        When
        serviceJpa.delete(contact);

//        Then
        Mockito.verify(contactRepository, Mockito.times(1)).delete(contact);
    }

    @Test
    void deleteById() {
//        Given
        Long id= 1L;

//        When
        serviceJpa.deleteById(id);

//        Then
        Mockito.verify(contactRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}