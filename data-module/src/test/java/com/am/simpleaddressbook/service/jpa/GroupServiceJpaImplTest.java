package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Group;
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

class GroupServiceJpaImplTest {

    @Mock
    GroupRepository groupRepository;

    GroupServiceJpaImpl serviceJpa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceJpa= new GroupServiceJpaImpl(groupRepository);
    }

    @Test
    void testSaveWhenIsGivenNull(){
//        Given
        String expectedMessage= "Group is NULL";

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
        Group group= new Group();
        group.setId(1L);
        Mockito.when(groupRepository.save(Mockito.any())).thenReturn(group);

//        When
        Group savedGroup= serviceJpa.save(group);

//        Then
        Assertions.assertEquals(group.getId(), savedGroup.getId());
        Mockito.verify(groupRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    @Test
    void findAll() {
//        Given
        Group group1= new Group();
        group1.setId(1L);
        Group group2= new Group();
        group2.setId(2L);
        Set<Group> groupSet= new HashSet<>();
        groupSet.add(group1);
        groupSet.add(group2);

        Mockito.when(groupRepository.findAll()).thenReturn(groupSet);

//        When
        Set<Group> groups= serviceJpa.findAll();

//        Then
        Assertions.assertEquals(2, groups.size());
    }

    @Test
    void testFindByIdWithNonExistId(){
//        Given
        String expectedMessage= "Group ID can not be null!!!";
        Mockito.when(groupRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

//        When
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                serviceJpa.findById(Mockito.anyLong());
            }
        });

//        Then
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void findById() {
//        Given
        Group group= new Group();
        group.setId(1L);
        Mockito.when(groupRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(group));

//        When
        Group foundGroup= serviceJpa.findById(Mockito.anyLong());

//        Then
        Assertions.assertNotNull(foundGroup);
        Assertions.assertEquals(1L, foundGroup.getId());
    }

    @Test
    void delete() {
//        Given
        Group group= new Group();

//        When
        serviceJpa.delete(group);

//        Then
        Mockito.verify(groupRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void deleteById() {
//        Given
        Long id= 4L;

//        When
        serviceJpa.deleteById(id);

//        Then
        Mockito.verify(groupRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}