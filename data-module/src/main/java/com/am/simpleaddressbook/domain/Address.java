package com.am.simpleaddressbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntry {
    private String country;
    private String city;
    private String street;
    private String houseNo;
}
