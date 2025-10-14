package com.moonstack.entity;

import com.moonstack.enums.PurchaseStatus;
import com.moonstack.enums.RequestStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance
@SuperBuilder
@SQLDelete(sql = "UPDATE expenses SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Expense extends AbstractPersistable
{
    private String invoiceNumber;
    private String itemName;
    private String purchasedBy;
    private LocalDate purchaseDate;
    private int purchaseAmount;

    private PurchaseStatus purchaseStatus;

    private RequestStatus status;

    private String approvedBy;
    private LocalDate approvedDate;

    private String invoiceFileName;
    private String fileType;

    private String user;
}
