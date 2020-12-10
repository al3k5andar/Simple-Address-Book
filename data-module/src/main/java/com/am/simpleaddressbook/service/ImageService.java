package com.am.simpleaddressbook.service;

import com.am.simpleaddressbook.domain.Contact;

public interface ImageService
{
    void setImage(byte[] image, Contact contact);

    Byte[] getImage(Long contactId);
}
