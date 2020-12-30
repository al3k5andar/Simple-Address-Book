package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.*;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

class ContactControllerTest {

    @Mock
    ContactService contactService;

    @Mock
    ImageService imageService;

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
        controller= new ContactController(contactService, imageService);

        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void contactType() {
        ContactType[] types= ContactType.values();

        Assertions.assertNotNull(types);
        Assertions.assertEquals(2, types.length);
    }

    @Test
    void showAllContactsTest() throws Exception {
//        Given
        Contact contact1= new Contact();
        Contact contact2= new Contact();
        Set<Contact> contacts= new HashSet<>();
        contacts.add(contact1);
        contacts.add(contact2);

        Mockito.when(contactService.findAll()).thenReturn(contacts);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("contacts"))
                .andExpect(MockMvcResultMatchers.view().name("contacts/index"));
//        Then
        Mockito.verify(contactService, Mockito.never()).save(Mockito.any());
    }

    @Test
    void showNewContactForm() throws Exception {
//        Given
        Contact contact= new Contact();
        contact.setId(1L);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("contact"))
                .andExpect(MockMvcResultMatchers.view().name("contacts/contact-form"));
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

        Mockito.when(contactService.save(Mockito.any())).thenReturn(contact);

        MockMultipartFile file= new MockMultipartFile("contactImage"
                ,"something.txt"
                ,"text/plain"
                ,"Spring".getBytes());

//        When
        mockMvc.perform(MockMvcRequestBuilders.multipart("/contacts/new").file(file)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("contactId","2L")
                    .param("firstName","Mike")
                    .param("lastName","Jonson")
                    .param("details.phone","33445575"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/contacts/1/details/view"));
//        Then
        Mockito.verify(contactService,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void showUpdateContactForm() throws Exception {
//        Given
        Contact contact= new Contact();
        contact.setId(1L);

        Mockito.when(contactService.findById(Mockito.anyLong())).thenReturn(contact);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("contact"))
                .andExpect(MockMvcResultMatchers.view().name("contacts/contact-form"));

//        Then
        Mockito.verify(contactService, Mockito.never()).save(Mockito.any());
    }

    @Test
    void processUpdateContactForm() throws Exception {
//        Given
        Contact contact= new Contact();
        contact.setId(2L);
        Details details= new Details();
        contact.setDetails(details);

        Mockito.when(contactService.findById(Mockito.anyLong())).thenReturn(contact);

        MockMultipartFile file= new MockMultipartFile("contactImage"
                ,"something.txt"
                ,"text/plane"
                ,"Spring".getBytes()
        );

//        Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/contacts/2/update").file(file)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("contactId","2L")
                    .param("firstName","Mike")
                    .param("lastName","Jonson")
                    .param("details.phone","33445575"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/contacts/2/details/view"));
//        Then
        Mockito.verify(contactService,Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(contactService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deleteContact() throws Exception {
//        Given;
        Contact contact= new Contact();
        contact.setId(1L);

        Mockito.when(contactService.findById(Mockito.anyLong())).thenReturn(contact);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/1/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/contacts/index"));

//        Then
        Mockito.verify(contactService,Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(contactService,Mockito.times(1)).findById(Mockito.anyLong());
    }
}