package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Address;
import com.am.simpleaddressbook.domain.Details;
import com.am.simpleaddressbook.repositories.DetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class DetailsServiceJpaImplTest {

    @Mock
    DetailsRepository detailsRepository;

    DetailsServiceJpaImpl serviceJpa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceJpa= new DetailsServiceJpaImpl(detailsRepository);
    }

    @Test
    void testSaveWithGivenNullParameter(){
        Details details= serviceJpa.save(null);
        Assertions.assertNotNull(details);
    }

    @Test
    void testSaveWithGivenDetailsWithoutAddress(){
//        Given
        Details theDetails= new Details();
        theDetails.setId(1L);
        theDetails.setAddress(null);
        Optional<Details> detailsOptional= Optional.of(theDetails);

        Mockito.when(detailsRepository.findById(Mockito.anyLong())).thenReturn(detailsOptional);
        Mockito.when(detailsRepository.save(Mockito.any())).thenReturn(theDetails);

//        When
        Details savedDetails= serviceJpa.save(detailsOptional.get());

//        Then
        Assertions.assertNotNull(savedDetails.getAddress());
    }

    @Test
    void testUpdate() {
//        Given
        Details theDetails= new Details();
        theDetails.setId(1L);
        Address address= new Address();
        address.setId(2L);
        theDetails.setAddress(address);

        Optional<Details> detailsOptional= Optional.of(theDetails);

        Mockito.when(detailsRepository.findById(Mockito.anyLong())).thenReturn(detailsOptional);
        Mockito.when(detailsRepository.save(Mockito.any())).thenReturn(theDetails);

//        When
        Details savedDetails= serviceJpa.save(detailsOptional.get());

//        Then
        Assertions.assertNotNull(savedDetails);
        Assertions.assertNotNull(savedDetails.getAddress());
        Assertions.assertEquals(1L, savedDetails.getId());
        Assertions.assertEquals(2L,savedDetails.getAddress().getId());
    }

    @Test
    void testSaveNewDetails(){
//        Given
        Details details= new Details();

        Mockito.when(detailsRepository.save(Mockito.any())).thenReturn(details);

//        When
        Details savedDetails= serviceJpa.save(details);

//        Then
        Assertions.assertNotNull(savedDetails);
    }

    @Test
    void findAll() {
//        Given
        Details details1= new Details();
        Details details2= new Details();
        Set<Details> detailsSet= new HashSet<>();
        detailsSet.add(details1);
        detailsSet.add(details2);

        Mockito.when(detailsRepository.findAll()).thenReturn(detailsSet);

//        When
        Set<Details> allDetails= serviceJpa.findAll();

//        Then
        Assertions.assertEquals(2, allDetails.size());
    }

    @Test
    void findById() {
//        Given
        Long id=1L;
        Details details= new Details();
        details.setId(id);

        Mockito.when(detailsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(details));

//        When
        Details theDetails= serviceJpa.findById(id);

//        Then
        Assertions.assertNotNull(theDetails);
        Assertions.assertEquals(id, theDetails.getId());
    }

    @Test
    void delete() {
//        Given
        Details details= new Details();

//        When
        serviceJpa.delete(details);

//        Then
        Mockito.verify(detailsRepository,Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void deleteById() {
//        Given
        Long id= 1L;

//        When
        serviceJpa.deleteById(id);

//        Then
        Mockito.verify(detailsRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}