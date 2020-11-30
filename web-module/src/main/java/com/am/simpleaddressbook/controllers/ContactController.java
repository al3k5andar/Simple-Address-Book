package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.*;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/groups/{groupId}/contacts")
public class ContactController {

    private final GroupService groupService;
    private final ContactService contactService;

    public ContactController(GroupService groupService,
                             ContactService contactService) {
        this.groupService = groupService;
        this.contactService = contactService;
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
    public String processNewContactForm(@PathVariable Long groupId, @ModelAttribute Contact contact){
        Contact savedContact= contactService.save(contact);
        Group group= groupService.findById(groupId);
        group.getContacts().add(savedContact);
        groupService.save(group);

        return "redirect:/groups/"+ groupId +"/view";
    }

    @GetMapping("/{contactId}/update")
    public String showUpdateContactForm(@PathVariable Long contactId, Model model){
        model.addAttribute("contact", contactService.findById(contactId));

        return "groups/details/contacts/contact-form";
    }

    @PostMapping("/{contactId}/update")
    public String processUpdateContactForm(@PathVariable Long contactId,
                                           @PathVariable Long groupId, @ModelAttribute Contact contact){
        contact.setId(contactId);

        Group savedGroup= groupService.findById(groupId);
        Contact updatedContact= savedGroup.getContacts().stream()
                .filter(theContact -> theContact.getId().equals(contactId))
                .findFirst().orElse(null);
        if(updatedContact!= null){
            updatedContact.setId(contactId);
            updatedContact.setFirstName(contact.getFirstName());
            updatedContact.setLastName(contact.getLastName());
            updatedContact.setMiddleName(contact.getMiddleName());
            updatedContact.setImage(contact.getImage());
            updatedContact.setContactType(contact.getContactType());
            updatedContact.setDetails(contact.getDetails());
            updatedContact.setNote(contact.getNote());
            updatedContact.setGroups(contact.getGroups());
        }
        contactService.save(updatedContact);
        groupService.save(savedGroup);

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
