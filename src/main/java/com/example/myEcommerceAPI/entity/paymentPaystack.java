// package com.example.myEcommerceAPI.entity;

// import java.math.BigDecimal;
// import java.util.Date;

// import org.hibernate.annotations.CreationTimestamp;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Temporal;
// import jakarta.persistence.TemporalType;

// public class paymentPaystack {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//     @JoinColumn(name = "user_id")
//     private UserProfile user;

//     @Column(name = "reference")
//     private String reference;

//     @Column(name = "amount")
//     private BigDecimal amount;

//     @Column(name = "gateway_response")
//     private String gatewayResponse;

//     @Column(name = "paid_at")
//     private String paidAt;

//     @Column(name = "created_at")
//     private String createdAt;

//     @Column(name = "channel")
//     private String channel;

//     @Column(name = "currency")
//     private String currency;

//     @Column(name = "ip_address")
//     private String ipAddress;

//     @Column(name = "pricing_plan_type", nullable = false)
//     @Enumerated(EnumType.STRING)
//     private pricing plan type PlanType = PricingPlanType BASIC;

//     @CreationTimestamp
//     @Temporal(TemporalType.TIMESTAMP)
//     @Column(name = "created_on", updatable = false, nullable = false)
//     private Date createdOn;
// }
