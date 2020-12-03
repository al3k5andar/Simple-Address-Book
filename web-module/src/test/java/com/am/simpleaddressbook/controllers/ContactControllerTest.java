package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.*;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ContactControllerTest {

    @Mock
    GroupService groupService;

    @Mock
    ContactService contactService;

    ContactController controller;

    MockMvc mockMvc;

    final Long id= 1L;
    final String firstName= "Alex";
    final String lastName= "Peterson";
    final String middleName= "Mark";
    final String phone= "3333";
    final String workPhone= "44444";
    final String email= "www.fff";
    final String country= "USA";
    final String city= "New York";
    final String contactType= "STANDARD";
    final String noteDescription= "some note";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller= new ContactController(groupService, contactService);

        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findGroupById() {
//        Given
        Group group= new Group();
        group.setId(1L);

        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);

//        When
        Group theGroup= groupService.findById(1L);

//        Then
        Assertions.assertEquals(1L, theGroup.getId());
        Mockito.verify(groupService, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void contactType() {
        ContactType[] types= ContactType.values();

        Assertions.assertNotNull(types);
        Assertions.assertEquals(2, types.length);
    }

    @Test
    void showNewContactForm() throws Exception {
//        Given
        Contact contact= new Contact();
        contact.setId(1L);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/1/contacts/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("contact"))
                .andExpect(MockMvcResultMatchers.view().name("groups/details/contacts/contact-form"));
//        Then
        Mockito.verify(contactService,Mockito.never()).save(Mockito.any());
    }

    @Test
    void processNewContactForm() throws Exception {
//        Given
        Contact contact= new Contact();
        contact.setId(id);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setMiddleName(middleName);
        contact.setContactType(ContactType.valueOf(contactType));

        Details details= new Details();
        details.setEmail(email);
        details.setPhone(phone);
        details.setWorkPhone(workPhone);

        Address address= new Address();
        address.setCountry(country);
        address.setCity(city);
        details.setAddress(address);
        contact.setDetails(details);

        Note note= new Note();
        note.setDescription(noteDescription);

        contact.setNote(note);

        Group group= new Group();
        group.setId(id);

        Mockito.when(contactService.save(Mockito.any())).thenReturn(contact);
        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);

//        When
        mockMvc.perform(MockMvcRequestBuilders.post("/groups/1/contacts/new")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("groupId","1L")
                    .param("contact","description"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups/1/view"));
//        Then
        Mockito.verify(contactService,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(groupService,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(groupService,Mockito.times(2)).findById(Mockito.anyLong());

    }

    @Test
    void showUpdateContactForm() throws Exception {
//        Given
        Contact contact= new Contact();
        contact.setId(1L);

        Mockito.when(contactService.findByGroupIdAndContactId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(contact);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/1/contacts/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("contact"))
                .andExpect(MockMvcResultMatchers.view().name("groups/details/contacts/contact-form"));

//        Then
        Mockito.verify(contactService, Mockito.never()).save(Mockito.any());
    }

    @Test
    void processUpdateContactForm() throws Exception {
//        Given
        Group group= new Group();
        group.setId(1L);
        Contact contact= new Contact();
        contact.setId(2L);

        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);
        Mockito.when(contactService.findById(Mockito.anyLong())).thenReturn(contact);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.post("/groups/1/contacts/2/update")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("contactId","2L")
                    .param("groupId","1L")
                    .param("contact","description"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups/1/view"));
//        Then
        Mockito.verify(groupService,Mockito.times(2)).findById(Mockito.anyLong());
        Mockito.verify(contactService,Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(groupService,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deleteContact() throws Exception {
//        Given
        Group group= new Group();
        group.setId(1L);
        Contact contact= new Contact();
        contact.setId(1L);

        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);
        Mockito.when(contactService.findById(Mockito.anyLong())).thenReturn(contact);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/1/contacts/1/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups/1/view"));

//        Then
        Mockito.verify(groupService, Mockito.times(2)).findById(Mockito.anyLong());
        Mockito.verify(contactService,Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(groupService,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(contactService,Mockito.times(1)).findById(Mockito.anyLong());
    }
}