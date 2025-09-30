package com.moonstack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbstractPersistable implements Serializable
{
    @Column(name = "ID")
    @Id
    private String id;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /*
    @Column(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "UPDATED_BY")
    private User updatedBy;
    */

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "IS_DELETED")
    private Boolean deleted = Boolean.FALSE;
}
