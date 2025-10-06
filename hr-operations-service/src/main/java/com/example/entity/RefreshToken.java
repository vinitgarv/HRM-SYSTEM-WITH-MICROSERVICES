//package com.example.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@SuperBuilder
//@Inheritance
//public class RefreshToken extends AbstractPersistable {
//    private String token;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    private LocalDateTime expiryDate;
//}
