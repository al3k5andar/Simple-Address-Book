package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.*;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import com.am.simpleaddressbook.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/groups/{groupId}/contacts")
public class ContactController {

    private final GroupService groupService;
    private final ContactService contactService;
    private final ImageService imageService;

    public ContactController(GroupService groupService,
                             ContactService contactService, ImageService imageService) {
        this.groupService = groupService;
        this.contactService = contactService;
        this.imageService = imageService;
    }

    @ModelAttribute("group")
    public Group findGroupById(@PathVariable Long groupId){
        return groupService.findById(groupId);
    }

    @ModelAttribute("contactType")
    public ContactType[] contactType(){
        return ContactType.values();
    }

    @GetMapping("/new")
    public ModelAndView showNewContactForm(){
        Contact contact= new Contact();
        Details details= new Details();
        Address address= new Address();
        details.setAddress(address);
        contact.setDetails(details);
        Note note= new Note();
        contact.setNote(note);

        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("contact", contact);

        modelAndView.setViewName("groups/details/contacts/contact-form");

        return modelAndView;
    }

    @PostMapping("/new")
    public String processNewContactForm(@PathVariable Long groupId, @ModelAttribute Contact contact,
                                        @RequestParam("contactImage") MultipartFile multipartFile) throws IOException {

        imageService.setImage(multipartFile.getBytes(),contact);
        Contact savedContact= contactService.save(contact);
        Group group= groupService.findById(groupId);
        group.getContacts().add(savedContact);
        groupService.save(group);

        return "redirect:/groups/"+ groupId +"/view";
    }

    @GetMapping("/{contactId}/update")
    public String showUpdateContactForm(@PathVariable Long groupId,
                                        @PathVariable Long contactId, Model model){
        model.addAttribute("contact", contactService.findByGroupIdAndContactId(groupId, contactId));

        return "groups/details/contacts/contact-form";
    }

    @PostMapping("/{contactId}/update")
    public String processUpdateContactForm(@PathVariable Long contactId,
                                           @PathVariable Long groupId,
                                           @ModelAttribute Contact contact, @RequestParam("contactImage") MultipartFile multipartFile) throws IOException {

        Group group= groupService.findById(groupId);
        Contact searchedContact= contactService.findById(contactId);
        if(searchedContact!= null) {
            searchedContact.setContactType(contact.getContactType());
            searchedContact.setImage(contact.getImage());
            searchedContact.setMiddleName(contact.getMiddleName());
            searchedContact.setFirstName(contact.getFirstName());
            searchedContact.setLastName(contact.getLastName());
            searchedContact.setDetails(contact.getDetails());
            searchedContact.setNote(contact.getNote());
            if(multipartFile!= null){
                imageService.setImage(multipartFile.getBytes(),searchedContact);
            }
            group.getContacts().add(searchedContact);
        }
        groupService.save(group);

        return "redirect:/groups/"+ groupId + "/view";
    }

    @GetMapping("/{contactId}/delete")
    public String deleteContact(@PathVariable Long contactId, @PathVariable Long groupId){
        Contact contact= contactService.findById(contactId);
        Group group= groupService.findById(groupId);

        group.getContacts().remove(contact);
        contactService.delete(contact);
        groupService.save(group);

        return "redirect:/groups/"+ groupId+ "/view";
    }
}
