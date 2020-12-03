package com.am.simpleaddressbook.bootstrap;

import com.am.simpleaddressbook.domain.*;
import com.am.simpleaddressbook.service.GroupService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final GroupService groupService;

    public DataLoader(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(groupService.findAll().size()== 0)
            groupList().forEach(groupService::save);
    }

    private List<Group> groupList(){
        List<Group> groups= new ArrayList<>();

//        New Group
        Group family= new Group();
        family.setName("Family Group");
        family.setDescription("Only family member contacts");

//        New Contact
        Contact sister= new Contact();
        sister.setContactType(ContactType.FAVORITE);
        sister.setFirstName("Snezana");
        sister.setMiddleName("Tomislav");
        sister.setLastName("Aleksic");

//        New sister note
        Note sisterNote= new Note();
        sisterNote.setDescription("Sister");
        sister.setNote(sisterNote);

//        New sister details
        Details sisterDetails= new Details();
        sisterDetails.setNickName("Nena");
        sisterDetails.setPhone("0649677047");
        sisterDetails.setEmail("www.snezana@example.com");
        sisterDetails.setBirthday(LocalDate.of(1991,1,30));

//        New sister address
        Address sisterAddress= new Address();
        sisterAddress.setCountry("Switzerland");
        sisterAddress.setCity("Wetzikon");
        sisterAddress.setStreet("Zurich");
        sisterAddress.setHouseNo("10A");
        sisterDetails.setAddress(sisterAddress);

        sister.setDetails(sisterDetails);

        family.getContacts().add(sister);
//        --------------------------------------------------------------

//        New Contact
        Contact mom= new Contact();
        mom.setFirstName("Silvana");
        mom.setLastName("Mitic");
        mom.setMiddleName("Jovica");
        mom.setContactType(ContactType.FAVORITE);

//        New mom note
        Note momNote= new Note();
        momNote.setDescription("Mom");
        mom.setNote(momNote);

//        New mom details
        Details momDetails= new Details();
        momDetails.setPhone("0643708077");
        momDetails.setEmail("www.silvana@example.com");
        momDetails.setBirthday(LocalDate.of(1969,4,24));

//        New mom address
        Address momAddress= new Address();
        momAddress.setCountry("Serbia");
        momAddress.setCity("Pirot");
        momAddress.setStreet("sl.Temsla");
        momAddress.setHouseNo("50");
        momDetails.setAddress(momAddress);

        mom.setDetails(momDetails);

        family.getContacts().add(mom);

        groups.add(family);

//        Group group1= new Group();
//        group1.setName("First");
//        group1.setDescription("First group");
//
//        Contact contact1= new Contact();
//        contact1.setFirstName("Patric");
//        contact1.setLastName("Simon");
//        Details contactDetails= new Details();
//        contactDetails.setNickName("Example nickname");
//
//        Address address= new Address();
//        address.setCountry("Serbia");
//        contactDetails.setAddress(address);
//        contact1.setDetails(contactDetails);
//
//        Note contactNote= new Note();
//        contactNote.setDescription("Some contact description");
//        contact1.addNote(contactNote);
//        group1.getContacts().add(contact1);
//
//        Group group2= new Group();
//        group2.setName("Second");
//        group2.setDescription("Second group");
//
//        groups.add(group1);
//        groups.add(group2);

        return groups;
    }
}
