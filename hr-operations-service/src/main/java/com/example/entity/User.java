//package com.example.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@SuperBuilder
//@Inheritance
//public class User extends AbstractPersistable {
//    private String firstName;
//    private String lastName;
//    private String accType;
//    private String email;
//    private String tempPassword;
//    private String password;
//    private int tokenVersion = 0;
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private Set<Role> roles = new LinkedHashSet<>();
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<LoginLogs> loginLogs;
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<AttendanceLogs> attendanceLogs;
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<AttendanceData> attendanceData;
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<AttendanceApproval> attendanceApprovals;
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<EmployeeLeaves> employeeLeaves;
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<EmployeeOvertime> employeeOvertime;
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<Warning> warnings;
//
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<TimeSheet> timeSheets;
//
//    public void addRole(Role role) {
//        roles.add(role);
//        role.setUser(this);
//    }
//    public void removeRole(Role role) {
//        roles.remove(role);
//        role.setUser(null);
//    }
//}