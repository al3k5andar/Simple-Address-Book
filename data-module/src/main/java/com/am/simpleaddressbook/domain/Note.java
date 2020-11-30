package com.am.simpleaddressbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class Note extends BaseEntry
{
    @Lob
    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "note")
    private Contact contact;
}
