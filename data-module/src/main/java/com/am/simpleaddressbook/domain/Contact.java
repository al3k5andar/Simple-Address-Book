package com.am.simpleaddressbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact")
public class Contact extends BaseEntry
{
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "image")
    @Lob
    private Byte[] image;

    @OneToOne
    @JoinColumn(name = "details_id")
    private Details details;

    @OneToOne()
    @JoinColumn(name = "note_id")
    private Note note;

    @Enumerated
    private ContactType contactType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_contact",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    Set<Group> groups= new HashSet<>();

}
