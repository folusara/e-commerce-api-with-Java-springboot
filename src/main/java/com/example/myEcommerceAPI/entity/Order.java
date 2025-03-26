package com.example.myEcommerceAPI.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private int quantity;
    private Date date;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;


    public Order() {
    }

    public Order(Long id, double price, int quantity, Date date, double amount, User user) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.amount = amount;
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order id(Long id) {
        setId(id);
        return this;
    }

    public Order price(double price) {
        setPrice(price);
        return this;
    }

    public Order quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public Order date(Date date) {
        setDate(date);
        return this;
    }

    public Order amount(double amount) {
        setAmount(amount);
        return this;
    }

    public Order user(User user) {
        setUser(user);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, quantity, date, amount, user);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", price='" + getPrice() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", date='" + getDate() + "'" +
            ", amount='" + getAmount() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }


    
}
