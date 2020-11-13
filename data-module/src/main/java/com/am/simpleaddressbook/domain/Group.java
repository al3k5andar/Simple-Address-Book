package com.am.simpleaddressbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group extends BaseEntry
{
    private String name;
    private String description;
    private Set<Contact> contacts= new HashSet<>();
}
