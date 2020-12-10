package com.am.simpleaddressbook.service.jpa;

import com.am.simpleaddressbook.domain.Contact;
import com.am.simpleaddressbook.repositories.ContactRepository;
import com.am.simpleaddressbook.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile("JPA")
@Slf4j
public class ImageServiceJpaImpl implements ImageService
{
    private final ContactRepository contactRepository;

    public ImageServiceJpaImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void setImage(byte[] image, Contact contact) {
        if (contact== null){
            log.info("Contact dont exists or is null!!!!");
            throw new RuntimeException("Contact is NULL!!!");
        }

        Byte[] imageBytes= new Byte[image.length];
        int counter=0;

        for (byte b : image) {
            imageBytes[counter++] = b;
        }
        contact.setImage(imageBytes);
    }

    @Override
    public Byte[] getImage(Long contactId) {
        Optional<Contact> optionalContact= contactRepository.findById(contactId);
        if(!optionalContact.isPresent()){
            log.info("Contact with ID:  "+ contactId +"dont exists or is null!!!!");
            throw new RuntimeException("Contact is NULL!!!");
        }
        Contact contact= optionalContact.get();
        Byte[] imageArray= null;
        if(contact.getImage()!= null) {
            imageArray = new Byte[contact.getImage().length];
            int counter= 0;
            for(Byte b: contact.getImage())
                imageArray[counter++]= b;
            return imageArray;
        }
        return null;
    }
}
