package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Address;
import com.am.simpleaddressbook.domain.Details;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class DetailsServiceMapImplTest {
    DetailsServiceMapImpl detailsServiceMap;

    Details details;

    Long id= 1L;
    LocalDate birthday= LocalDate.now();
    String phone= "000333";
    String workPhone= "333225";
    String email= "www";
    String nickName= "nick";
    Address address= new Address();

    @BeforeEach
    void setUp() {
        detailsServiceMap= new DetailsServiceMapImpl(new AddressServiceMapImpl());

        details= new Details();
    }

    @Test
    void save() {
//        Given
        details.setId(id);
        details.setBirthday(birthday);
        details.setEmail(email);
        details.setNickName(nickName);
        details.setPhone(phone);
        details.setWorkPhone(workPhone);
        details.setAddress(address);

//        When
        Details savedDetails= detailsServiceMap.save(details);

//        Then
        Assertions.assertNotNull(savedDetails);
        Assertions.assertEquals(id, savedDetails.getId());
        Assertions.assertEquals(birthday, savedDetails.getBirthday());
        Assertions.assertEquals(nickName, savedDetails.getNickName());
        Assertions.assertEquals(phone, savedDetails.getPhone());
        Assertions.assertEquals(workPhone, savedDetails.getWorkPhone());
        Assertions.assertEquals(email, savedDetails.getEmail());
        Assertions.assertEquals(1L, savedDetails.getAddress().getId());
    }

    @Test
    void findAll() {
//        Given
        Details details1= new Details();
        Details details2= new Details();

        detailsServiceMap.save(details1);
        detailsServiceMap.save(details2);

//        When
        Set<Details> details= detailsServiceMap.findAll();

//        Then
        Assertions.assertEquals(2, details.size());
    }

    @Test
    void findById() {
//        Given
        details.setId(id);
        detailsServiceMap.save(details);

//        When
        Details theDetails= detailsServiceMap.findById(id);

//        Then
        Assertions.assertEquals(id, theDetails.getId());

    }

    @Test
    void delete() {
//        Given
        details.setId(id);
        detailsServiceMap.save(details);

//        When
        detailsServiceMap.delete(details);

//        Then
        Assertions.assertEquals(0, detailsServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
//        Given
        details.setId(id);
        detailsServiceMap.save(details);

//        When
        detailsServiceMap.deleteById(id);

//        Then
        Assertions.assertEquals(0, detailsServiceMap.findAll().size());
    }
}