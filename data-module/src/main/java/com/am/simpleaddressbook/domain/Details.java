package com.am.simpleaddressbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "details")
public class Details extends BaseEntry
{
    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "work_phone")
    private String workPhone;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickName;

    @ManyToOne()
    @JoinColumn(name = "address_id")
    private Address address;
}
