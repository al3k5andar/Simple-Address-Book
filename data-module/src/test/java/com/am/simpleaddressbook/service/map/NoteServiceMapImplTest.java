package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class NoteServiceMapImplTest {
    NoteServiceMapImpl serviceMap;

    Note note;

    Long id= 1L;
    String description= "some description";

    @BeforeEach
    void setUp() {
        serviceMap= new NoteServiceMapImpl();

        note= new Note();
    }

    @Test
    void save() {
//        Given
        note.setId(id);
        note.setDescription(description);

//        When
        Note savedNote= serviceMap.save(note);

//        Then
        Assertions.assertNotNull(savedNote);
        Assertions.assertEquals(id, savedNote.getId());
        Assertions.assertEquals(description, savedNote.getDescription());
    }

    @Test
    void findAll() {
//        Given
        Note note1= new Note();
        Note note2= new Note();

        serviceMap.save(note1);
        serviceMap.save(note2);

//        When
        Set<Note> notes= serviceMap.findAll();

//        Then
        Assertions.assertEquals(notes.size(), serviceMap.findAll().size());
    }

    @Test
    void findById() {
//        Given
        note.setId(id);
        serviceMap.save(note);

//        When
        Note theNote= serviceMap.findById(id);

//        Then
        Assertions.assertEquals(id, theNote.getId());
    }

    @Test
    void delete() {
//        Given
        note.setId(id);
        serviceMap.save(note);

//        When
        serviceMap.delete(note);

//        Then
        Assertions.assertEquals(0, serviceMap.findAll().size());
    }

    @Test
    void deleteById() {
//        Given
        note.setId(id);
        serviceMap.save(note);

//        When
        serviceMap.deleteById(id);

//        Then
        Assertions.assertEquals(0, serviceMap.findAll().size());
    }
}