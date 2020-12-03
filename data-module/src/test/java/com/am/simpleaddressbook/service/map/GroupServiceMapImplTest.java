package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class GroupServiceMapImplTest {

    GroupServiceMapImpl groupServiceMap;


    Group group;

    Long id= 1L;
    String name= "Family";
    String description= "Only my family members";

    @BeforeEach
    void setUp() {
        groupServiceMap= new GroupServiceMapImpl(new ContactServiceMapImpl(new NoteServiceMapImpl(),
                new DetailsServiceMapImpl(new AddressServiceMapImpl())));

        group= new Group();
    }

    @Test
    void save() {
//        Given
        group.setId(id);
        group.setName(name);
        group.setDescription(description);
        Contact contact1= new Contact();
        Contact contact2= new Contact();

        Set<Contact> contactSet= new HashSet<>();
        contactSet.add(contact1);
        contactSet.add(contact2);
        group.setContacts(contactSet);

//        When
        Group savedGroup= groupServiceMap.save(group);

//        Then
        Assertions.assertNotNull(savedGroup);
        Assertions.assertEquals(id, savedGroup.getId());
        Assertions.assertEquals(name, savedGroup.getName());
        Assertions.assertEquals(description, savedGroup.getDescription());
        Assertions.assertEquals(2, savedGroup.getContacts().size());
    }

    @Test
    void findAll() {
//        Given
        Group group1= new Group();
        Group group2= new Group();

        groupServiceMap.save(group1);
        groupServiceMap.save(group2);

//        When
        Set<Group> groups= groupServiceMap.findAll();

//        Then
        Assertions.assertEquals(2, groups.size());

    }

    @Test
    void findById() {
//        Given
        group.setId(id);
        groupServiceMap.save(group);

//        When
        Group theGroup= groupServiceMap.findById(id);

//        Then
        Assertions.assertEquals(id, theGroup.getId());
    }

    @Test
    void delete() {
//        Given
        group.setId(id);
        groupServiceMap.save(group);

//        When
        groupServiceMap.delete(group);

//        Then
        Assertions.assertEquals(0, groupServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
//        Given
        group.setId(id);
        groupServiceMap.save(group);

//        When
        groupServiceMap.deleteById(id);

//        Then
        Assertions.assertEquals(0, groupServiceMap.findAll().size());
    }
}