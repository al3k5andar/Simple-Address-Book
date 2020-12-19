package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/groups/{groupId}")
public class GroupDetailsController {

    private final GroupService groupService;
    private final ContactService contactService;

    public GroupDetailsController(GroupService groupService, ContactService contactService) {
        this.groupService = groupService;
        this.contactService = contactService;
    }

    @ModelAttribute("group")
    public Group findGroupById(@PathVariable Long groupId){
        return groupService.findById(groupId);
    }

    @GetMapping("/view")
    public ModelAndView showGroupDetails(){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("groups/details/index");

        return modelAndView;
    }

    @GetMapping("/select")
    public String showSelectContactForm(Model model){
        model.addAttribute("contacts", contactService.findAll());

        return "groups/details/add-contact-form";
    }

    @PostMapping("/select")
    public String processSelectContactForm(@ModelAttribute("group") Group group,
                                           @RequestParam("contactSelection") Long contactSelection){
        Contact contact= group.getContacts().stream()
                .filter(theContact -> theContact.getId().equals(contactSelection))
                .findFirst().orElse(null);
        if(contact== null) {
            Contact chosenContact = contactService.findById(contactSelection);
            group.getContacts().add(chosenContact);
            groupService.save(group);
        }

        return "redirect:/groups/" + group.getId() +"/view";
    }

    @GetMapping("contacts/{contactId}/remove")
    public String removeContact(@PathVariable("groupId") Long groupId,
                                @PathVariable("contactId") Long contactId){
        Group group= groupService.findById(groupId);

        Optional<Contact> contactOptional= group.getContacts().stream()
                .filter(theContact -> theContact.getId().equals(contactId)).findFirst();
        contactOptional.ifPresent(contact -> group.getContacts().remove(contact));

        groupService.save(group);

        return "redirect:/groups/" + group.getId() + "/view";
    }
}
