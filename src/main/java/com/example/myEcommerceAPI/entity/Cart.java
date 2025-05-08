package com.example.myEcommerceAPI.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
     UserProfile createdBy;

     // @ManyToMany(cascade = CascadeType.ALL)
     // @JoinTable(
     //    name = "cart_products",
     //    joinColumns = @JoinColumn(name = "cart_id"),
     //    inverseJoinColumns = @JoinColumn(name = "product_id")
     // )
//     @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//     @JoinColumn(name = "cart_id")
     @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<CartProduct> products = new ArrayList<>();
     // List<CartProduct> products = new ArrayList<>();
     
     private double total;
}
