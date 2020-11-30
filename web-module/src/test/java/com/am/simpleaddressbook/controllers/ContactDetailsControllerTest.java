package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ContactDetailsControllerTest {

    @Mock
    GroupService groupService;

    @Mock
    ContactService contactService;

    ContactDetailsController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller= new ContactDetailsController(groupService, contactService);

        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findGroupById() {
//        Given
        Long id= 1L;
        Group group= new Group();
        group.setId(id);

        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);

//        When
        Group theGroup= groupService.findById(id);

//        Then
        Assertions.assertNotNull(theGroup);
        Assertions.assertEquals(id, theGroup.getId());
    }

    @Test
    void findContactById() {
//        Given
        Long id= 1L;
        Contact contact= new Contact();
        contact.setId(id);

        Mockito.when(contactService.findById(Mockito.anyLong())).thenReturn(contact);

//        When
        Contact theContact= contactService.findById(id);

//        Then
        Assertions.assertNotNull(theContact);
        Assertions.assertEquals(id, theContact.getId());
    }

    @Test
    void showContactDetails() throws Exception {
//        Given
        Long groupId= 1L;
        Long contactId= 2L;
        Group group= new Group();
        group.setId(groupId);
        Contact contact= new Contact();
        contact.setId(contactId);
        group.getContacts().add(contact);


        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/1/contacts/1/details/view"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.view().name("groups/details/contacts/contact-info"));

//        Then
        Mockito.verify(groupService,Mockito.times(2)).findById(Mockito.anyLong());
    }
}