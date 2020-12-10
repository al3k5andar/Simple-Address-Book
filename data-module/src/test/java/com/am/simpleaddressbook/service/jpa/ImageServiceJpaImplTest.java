package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.repositories.ContactRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class ImageServiceJpaImplTest {

    @Mock
    ContactRepository contactRepository;

    ImageServiceJpaImpl serviceJpa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceJpa= new ImageServiceJpaImpl(contactRepository);
    }

    @Test
    void testGivenNullContact(){
//        Given
        String expectedMessage= "Contact is NULL!!!";

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                serviceJpa.setImage(null, null);
            }
        });

//        Then
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void setImage() {
//        Given
        Contact contact= new Contact();
        contact.setId(1L);
        byte[] someArray= "This is a picture".getBytes();
        int size= someArray.length;

//        When
        serviceJpa.setImage(someArray, contact);

//        Then
        Assertions.assertNotNull(contact.getImage());
        Assertions.assertEquals(size, contact.getImage().length);
    }

    @Test
    void testGetImageWithNonExistContactId(){
//        Given
        String expectedException= "Contact is NULL!!!";
        Optional<Contact> optionalContact= Optional.empty();
        Mockito.when(contactRepository.findById(Mockito.anyLong())).thenReturn(optionalContact);

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                serviceJpa.getImage(Mockito.anyLong());
            }
        });
//        Then
        Assertions.assertEquals(expectedException, exception.getMessage());
    }

    @Test
    void testContactWithoutImage(){
//        Given
        Contact contact= new Contact();
        contact.setImage(null);
        Mockito.when(contactRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(contact));

//        When
        Byte[] bytes= serviceJpa.getImage(Mockito.anyLong());

//        Then
        Assertions.assertNull(bytes);
    }

    @Test
    void getImage() {
//        Given
        Contact contact= new Contact();
        byte[] someBytes= "This is an image".getBytes();
        serviceJpa.setImage(someBytes, contact);
        Mockito.when(contactRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(contact));

//        When
        Byte[] theBytes= serviceJpa.getImage(Mockito.anyLong());

//        Then
        Assertions.assertNotNull(theBytes);
        Assertions.assertEquals(someBytes.length, theBytes.length);

    }
}