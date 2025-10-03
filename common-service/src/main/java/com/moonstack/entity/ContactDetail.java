package com.moonstack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SuperBuilder
@Inheritance
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE contact_detail SET IS_DELETED = true WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class ContactDetail extends AbstractPersistable {

    private String workPhoneNumber;
    private String personalMobile;
    private String extension;
    private String personalEmail;
    private String seatingLocation;
    private String tag;
    private String presentAddress;
    private String permanentAddress;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
