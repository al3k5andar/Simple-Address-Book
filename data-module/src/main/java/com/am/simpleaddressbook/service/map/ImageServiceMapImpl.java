package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.service.ContactService;
import com.am.simpleaddressbook.service.ImageService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("MAP")
public class ImageServiceMapImpl implements ImageService {
    private final ContactService contactService;

    public ImageServiceMapImpl(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void setImage(byte[] image, Contact contact) {
        if(contact== null){
            throw new RuntimeException("Contact object is NULL!!!");
        }
        Byte[] theImage= new Byte[image.length];
        int counter= 0;

        for(byte b: image)
            theImage[counter++]= b;

        contact.setImage(theImage);
        contactService.save(contact);
    }

    @Override
    public Byte[] getImage(Long contactId) {
        Contact contact= contactService.findById(contactId);
        return contact.getImage();
    }
}
