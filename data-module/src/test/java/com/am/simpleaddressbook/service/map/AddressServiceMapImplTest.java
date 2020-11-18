package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Set;

class AddressServiceMapImplTest {

    AddressServiceMapImpl serviceMap;
    Address address;
    Long id= 1L;
    String country= "Serbia";
    String city= "Belgrade";
    String street= "Winners Street";
    String houseNo= "30";

    @BeforeEach
    void setUp() {
        serviceMap= new AddressServiceMapImpl();
        address= new Address();
    }

    @Test
    void save() {
//        Given
        address.setId(id);
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setHouseNo(houseNo);

//        When
        Address savedAddress= serviceMap.save(address);

//        Then
        Assertions.assertNotNull(savedAddress);
        Assertions.assertEquals(id, savedAddress.getId());
        Assertions.assertEquals(country, savedAddress.getCountry());
        Assertions.assertEquals(city, savedAddress.getCity());
        Assertions.assertEquals(street, savedAddress.getStreet());
        Assertions.assertEquals(houseNo, savedAddress.getHouseNo());

    }

    @Test
    void testGivenNullParameterThenThrowException(){
        Exception exception= Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                serviceMap.save(null);
            }
        });
        String expected= "System can't save null object";
        String actual= exception.getMessage();

        Assertions.assertTrue(expected.contains(actual));
    }

    @Test
    void findAll() {
//        Given
        Address address1= new Address();
        Address address2= new Address();

        serviceMap.save(address1);
        serviceMap.save(address2);

//        When
        Set<Address> addressSet= serviceMap.findAll();

//        Then
        Assertions.assertEquals(addressSet.size(), serviceMap.findAll().size());
    }

    @Test
    void findById() {
//        Given
        address.setId(id);
        serviceMap.save(address);

//        When
        Address theAddress= serviceMap.findById(id);

//        Then
        Assertions.assertEquals(id, theAddress.getId());
    }

    @Test
    void delete() {
//        Given
        address.setId(id);
        serviceMap.save(address);

//        When
        serviceMap.delete(address);

//        Then
        Assertions.assertEquals(0, serviceMap.findAll().size());
    }

    @Test
    void deleteById() {
//        Given
        address.setId(id);
        serviceMap.save(address);

//        When
        serviceMap.deleteById(id);

//        Then
        Assertions.assertEquals(0, serviceMap.findAll().size());

    }
}