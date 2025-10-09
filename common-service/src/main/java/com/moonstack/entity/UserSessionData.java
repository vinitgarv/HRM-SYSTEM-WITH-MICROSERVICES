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
@SQLDelete(sql = "UPDATE USER_SESSION_DATA SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class UserSessionData extends AbstractPersistable
{
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String accessToken;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private DeviceData deviceData;
}
