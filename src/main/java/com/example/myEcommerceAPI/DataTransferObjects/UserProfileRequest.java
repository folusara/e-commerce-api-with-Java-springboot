package com.example.myEcommerceAPI.DataTransferObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import com.example.myEcommerceAPI.enums.Role;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserProfileRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String profileImageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    private String shippingAddress;
    private String billingAddress;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    
  

    private String preferredPaymentMethod; // e.g., "CREDIT_CARD"
    private String paymentDetails; // For encryption/masking
    private Integer loyaltyPoints = 0;

    private String accountStatus; // ACTIVE, INACTIVE, SUSPENDED
    private LocalDateTime registrationDate = LocalDateTime.now();
    private LocalDateTime lastLogin;
    private Boolean isVerified = false;
 
    public UserProfileRequest() {
        
    }

    public UserProfileRequest(String firstName, String lastName, String email, String phoneNumber,
            LocalDate dateOfBirth, String gender, String profileImageUrl, Collection<Role> roles,
            String shippingAddress, String billingAddress, String city, String state, String country, String postalCode,
            String preferredPaymentMethod, String paymentDetails, Integer loyaltyPoints, String accountStatus,
            LocalDateTime registrationDate, LocalDateTime lastLogin, Boolean isVerified) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
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
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
        this.isVerified = isVerified;
    }
    


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Collection<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getShippingAddress() {
        return this.shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return this.billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPreferredPaymentMethod() {
        return this.preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(String preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    public String getPaymentDetails() {
        return this.paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Integer getLoyaltyPoints() {
        return this.loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getAccountStatus() {
        return this.accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDateTime getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean isIsVerified() {
        return this.isVerified;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }



}
