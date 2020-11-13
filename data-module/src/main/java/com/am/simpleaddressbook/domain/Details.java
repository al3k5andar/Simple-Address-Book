package com.am.simpleaddressbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Details extends BaseEntry
{
    private LocalDate birthday;
    private String phone;
    private String workPhone;
    private String email;
    private String nickName;
    private Address address;
}
