package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/groups/{groupId}/contacts/{contactId}/details")
public class ContactDetailsController {

    private final GroupService groupService;
    private final ContactService contactService;

    public ContactDetailsController(GroupService groupService, ContactService contactService) {
        this.groupService = groupService;
        this.contactService = contactService;
    }

    @ModelAttribute("group")
    public Group findGroupById(@PathVariable Long groupId){
        return groupService.findById(groupId);
    }

    @ModelAttribute("contact")
    public Contact findContactById(@PathVariable Long contactId){
        return contactService.findById(contactId);
    }

    @GetMapping("/view")
    public ModelAndView showContactDetails(@PathVariable Long groupId,
                                           @PathVariable Long contactId){
        ModelAndView modelAndView= new ModelAndView();
        Group group= groupService.findById(groupId);
        Contact contact= group.getContacts().stream()
                .filter(theContact -> theContact.getId().equals(contactId))
                .findFirst().orElse(null);

        modelAndView.addObject("group", group);
        modelAndView.addObject("contact", contact);
        modelAndView.setViewName("groups/details/contacts/contact-info");

        return modelAndView;
    }
}
