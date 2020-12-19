package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.*;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final ImageService imageService;

    public ContactController(ContactService contactService, ImageService imageService) {
        this.contactService = contactService;
        this.imageService = imageService;
    }

    @ModelAttribute("contactType")
    public ContactType[] contactType(){
        return ContactType.values();
    }

    @GetMapping({"","/","index"})
    public String showAllContacts(Model model){
        model.addAttribute("contacts", contactService.findAll());

        return "contacts/index";
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

        modelAndView.setViewName("contacts/contact-form");

        return modelAndView;
    }

    @PostMapping("/new")
    public String processNewContactForm(@ModelAttribute Contact contact,
                                        @RequestParam("contactImage") MultipartFile multipartFile) throws IOException {

        imageService.setImage(multipartFile.getBytes(),contact);
        Contact savedContact= contactService.save(contact);

        return "redirect:/contacts/"+ savedContact.getId() +"/details/view";
    }

    @GetMapping("/{contactId}/update")
    public String showUpdateContactForm(@PathVariable Long contactId, Model model){
        model.addAttribute("contact", contactService.findById(contactId));

        return "contacts/contact-form";
    }

    @PostMapping("/{contactId}/update")
    public String processUpdateContactForm(@PathVariable Long contactId,
                                           @ModelAttribute Contact contact,
                                           @RequestParam("contactImage") MultipartFile multipartFile) throws IOException {

        Contact searchedContact= contactService.findById(contactId);
        if(searchedContact!= null) {
            searchedContact.setContactType(contact.getContactType());
            searchedContact.setMiddleName(contact.getMiddleName());
            searchedContact.setFirstName(contact.getFirstName());
            searchedContact.setLastName(contact.getLastName());
            searchedContact.setDetails(contact.getDetails());
            searchedContact.setNote(contact.getNote());
            if(multipartFile.getBytes().length != 0){
                imageService.setImage(multipartFile.getBytes(),searchedContact);
            }
        }
        contactService.save(searchedContact);

        return "redirect:/contacts/"+ contactId + "/details/view";
    }

    @GetMapping("/{contactId}/delete")
    public String deleteContact(@PathVariable Long contactId){
        Contact contact= contactService.findById(contactId);

        contactService.delete(contact);

        return "redirect:/contacts/index";
    }

    @GetMapping("/search")
    public String ShowSearchForm(Model model){
        model.addAttribute("contacts", contactService.findAll());
        return "contacts/find-contact-form";
    }

    @PostMapping("/processSearch")
    public String processSearchForm(@RequestParam("search") String searchWord,
                                    Model model){
        if(searchWord.isBlank())
            return "redirect:/contacts/search";
        List<Contact> contactList= contactService.findByLastNameLike("%"+searchWord+"%");
        if(contactList.size()== 1){
            Contact contact= contactList.get(0);
            model.addAttribute("contact", contact);
            return "redirect:/contacts/"+ contact.getId()+ "/details/view";
        }
        else {
            model.addAttribute("contacts", contactList);
            return "contacts/find-contact-form";
        }
    }
}
