package com.am.simpleaddressbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends BaseEntry
{
    private String firstName;
    private String lastName;
    private String middleName;
    private Byte[] image;
    private Details details;
    private Note note;
    private ContactType contactType;
    private Group group;

}
