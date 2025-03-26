package com.example.myEcommerceAPI.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.example.myEcommerceAPI.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_details")
@Getter
@Setter
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JsonIgnore
    // @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    // private User user;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    @JsonIgnore
    private User user;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    private String shippingAddress;
    private String billingAddress;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    private String preferredPaymentMethod;
    private String paymentDetails;
    private Integer loyaltyPoints = 0;

    private String accountStatus;
    private LocalDateTime registrationDate = LocalDateTime.now();
    private LocalDateTime lastLogin;
    private Boolean isVerified = false;

    @Version
    private Long version;

    public UserProfile(User user, String firstName, String lastName, String email, String phoneNumber,
                       LocalDate dateOfBirth, String gender, List<Role> roles,
                       String shippingAddress, String billingAddress, String city, String state, String country,
                       String postalCode, String preferredPaymentMethod, String paymentDetails,
                       Integer loyaltyPoints, String accountStatus, LocalDateTime lastLogin, Boolean isVerified) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.roles = roles;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.paymentDetails = paymentDetails;
        this.loyaltyPoints = loyaltyPoints;
        this.accountStatus = accountStatus;
        this.version = version;
        this.lastLogin = lastLogin;
        this.isVerified = isVerified;
    }

    public UserProfile orElse(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }
}
