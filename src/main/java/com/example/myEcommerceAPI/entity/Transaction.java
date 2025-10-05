package com.example.myEcommerceAPI.entity;

import java.time.LocalDateTime;

import com.example.myEcommerceAPI.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentReference;

    private double amountPaid;

    private String paymentMethod;

    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    private LocalDateTime transactionDate;
}
