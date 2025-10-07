package com.moonstack.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@SQLDelete(sql = "UPDATE SESSION_LOGS SET IS_DELETED = true WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class SessionLogs extends AbstractPersistable
{
    private String action;
    private String reason;
    private String status;
    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
