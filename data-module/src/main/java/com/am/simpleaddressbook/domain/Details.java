package com.am.simpleaddressbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "details")
public class Details extends BaseEntry
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private LocalDate birthday;

    @NotNull
    @Size(min = 6, max = 13, message = "Phone number length must be between 6 and 13 digits")
    @Column(name = "phone")
    private String phone;

    @Column(name = "work_phone")
    private String workPhone;

    @Email(message = "Must be a well-formed email address like: 'example@something.com'")
    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickName;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "address_id")
    private Address address;
}
