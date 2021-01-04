package com.am.simpleaddressbook.bootstrap;

import com.am.simpleaddressbook.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaticData
{
    public static List<Contact> contactList(){
        List<Contact> contacts= new ArrayList<>();

//        New Contact
        Contact uncle= new Contact();
        uncle.setContactType(ContactType.FAVORITE);
        uncle.setFirstName("John");
        uncle.setMiddleName("Jack");
        uncle.setLastName("Doe");

//        New uncle note
        Note sisterNote= new Note();
        sisterNote.setDescription("Uncle");
        uncle.setNote(sisterNote);

//        New uncle details
        Details sisterDetails= new Details();
        sisterDetails.setNickName("Johnny");
        sisterDetails.setPhone("333444555");
        sisterDetails.setEmail("jonny_doe@example.com");
        sisterDetails.setBirthday(LocalDate.of(1991,1,30));

//        New uncle address
        Address sisterAddress= new Address();
        sisterAddress.setCountry("Switzerland");
        sisterAddress.setCity("Zurich");
        sisterAddress.setStreet("High street");
        sisterAddress.setHouseNo("10A");
        sisterDetails.setAddress(sisterAddress);

        uncle.setDetails(sisterDetails);
        contacts.add(uncle);

//        --------------------------------------------------------------

//        New Contact
        Contact mom= new Contact();
        mom.setFirstName("Sandra");
        mom.setLastName("Simpson");
        mom.setMiddleName("Ali");
        mom.setContactType(ContactType.FAVORITE);

//        New mom note
        Note momNote= new Note();
        momNote.setDescription("Mom");
        mom.setNote(momNote);

//        New mom details
        Details momDetails= new Details();
        momDetails.setPhone("442299981");
        momDetails.setEmail("sandra@example.com");
        momDetails.setBirthday(LocalDate.of(1969,4,24));

//        New mom address
        Address momAddress= new Address();
        momAddress.setCountry("Canada");
        momAddress.setCity("Vancouver");
        momAddress.setStreet("First boulevard");
        momAddress.setHouseNo("50");
        momDetails.setAddress(momAddress);

        mom.setDetails(momDetails);

        contacts.add(mom);


        return contacts;
    }

    public static List<Group> groupList(){
        List<Group> groups= new ArrayList<>();

        Group family= new Group();
        family.setName("Family");
        family.setDescription("Only people in my family");

        groups.add(family);

        Group friends= new Group();
        friends.setName("Friends");
        friends.setDescription("Only my close friends");

        groups.add(friends);

        return groups;
    }
}
