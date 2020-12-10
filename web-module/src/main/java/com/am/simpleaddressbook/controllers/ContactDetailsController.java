package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.GroupService;
import com.am.simpleaddressbook.service.ImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Controller
@RequestMapping("/groups/{groupId}/contacts/{contactId}/details")
public class ContactDetailsController {

    private final GroupService groupService;
    private final ContactService contactService;
    private final ImageService imageService;

    @Autowired
    private ResourceLoader loader;

    public ContactDetailsController(GroupService groupService, ContactService contactService, ImageService imageService) {
        this.groupService = groupService;
        this.contactService = contactService;
        this.imageService = imageService;
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

    @GetMapping("/image")
    public void renderImageFormDataBase(@PathVariable("contactId") Long contactId, HttpServletResponse response) throws IOException {
        Logger logger= Logger.getGlobal();

        Contact contact= contactService.findById(contactId);
        byte[] imageBytes= null;
        InputStream is= null;
        if(contact.getImage()!= null){
           imageBytes = new byte[contact.getImage().length];
            int counter= 0;
            for(Byte b: contact.getImage())
                imageBytes[counter++]= b;
            is= new ByteArrayInputStream(imageBytes);
        }
        else {
            is= loader.getResource("classpath:static/images/EmptyPicture.png").getInputStream();
        }
        response.setContentType("image/jpeg");
        IOUtils.copy(is, response.getOutputStream());
    }
}
