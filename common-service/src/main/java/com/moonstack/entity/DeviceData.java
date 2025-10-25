package com.moonstack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@SuperBuilder
@Inheritance
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DeviceData extends AbstractPersistable
{
        private String deviceName;

        @Lob
        @Column(columnDefinition = "LONGTEXT")
        private String deviceId;

        private String ipAddress;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @OneToMany(mappedBy = "deviceData", cascade = CascadeType.ALL)
        private List<UserSessionData> sessionData;
}
