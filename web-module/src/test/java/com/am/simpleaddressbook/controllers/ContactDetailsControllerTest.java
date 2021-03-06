package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.exception.ErrorNotFoundException;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import com.am.simpleaddressbook.service.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ContactDetailsControllerTest {

    @Mock
    GroupService groupService;

    @Mock
    ContactService contactService;

    @Mock
    ImageService imageService;

    ContactDetailsController controller;

    @Autowired
    ResourceLoader resourceLoader;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller= new ContactDetailsController(groupService, contactService, imageService);

        mockMvc= MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
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
        Long contactId= 2L;
        Contact contact= new Contact();
        contact.setId(contactId);



        Mockito.when(contactService.findById(Mockito.anyLong())).thenReturn(contact);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/1/details/view"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("contact"))
                .andExpect(MockMvcResultMatchers.view().name("contacts/contact-info"));

//        Then
        Mockito.verify(contactService,Mockito.times(2)).findById(Mockito.anyLong());
    }

    @Test
    void renderImageFormDataBase() throws Exception {
//        Given
        Contact contact= new Contact();
        byte[] someImage= "This is an image".getBytes();
        Byte[] contactImage= new Byte[someImage.length];
        int counter= 0;
        for(byte b: someImage)
            contactImage[counter++]= b;
        contact.setImage(contactImage);
        Mockito.when(contactService.findById(Mockito.anyLong())).thenReturn(contact);

//        When
        MockHttpServletResponse response= mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/contacts/1/details/image"))
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andReturn().getResponse();

//        Then
        byte[] responseArray= response.getContentAsByteArray();
        Assertions.assertEquals(someImage.length, responseArray.length);

    }

    @Test
    void showBadRequestError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/something/details/view"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("errors/error400"));
    }

    @Test
    void showNotFoundError() throws Exception {

        Mockito.when(contactService.findById(Mockito.anyLong())).thenThrow(ErrorNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/contacts/34443/details/view"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("errors/error404"));
    }
}