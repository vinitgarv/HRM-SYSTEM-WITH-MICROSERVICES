package com.moonstack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Inheritance
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE USER SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class User extends AbstractPersistable
{
    private String firstName;
    private String lastName;
    private String accType;
    private String email;
    private String tempPassword;
    private String password;
    private int tokenVersion = 0;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Role> roles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SessionLogs> sessionLogs;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private ContactDetail contactDetail;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<DependentDetail> dependentDetails;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<EducationDetail> educationDetails;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<HierarchyInfo> hierarchyInfos;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private IdentityInfo identityInfo;

    @OneToOne( mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private PersonalDetail personalDetail;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<RelatedForm> relatedForms;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private SystemField systemField;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<WorkExperience> workExperiences;

    @OneToOne( mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private WorkInfo workInfo;


    public void addRole(Role role) {
        roles.add(role);
        role.setUser(this);
    }
    public void removeRole(Role role) {
        roles.remove(role);
        role.setUser(null);
    }

}
