package com.example.myEcommerceAPI.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Price is required")
    private double price;

    @Column(unique = true)
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")
    private String name;
    @NotBlank(message = "Product description is required")
    private String description;
    @NotBlank(message = "Product image is required")
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "", cascade = CascadeType.ALL)
    private List<Order> order;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Product category is required")
    private Category category;

    @ManyToMany 
    @JoinTable(
        name = "product_tags",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @NotNull(message = "Kindly add at least one product Tag")
    private List<Tag> tags;
    
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Inventory inventory;

    public Product(double price, String name, String description, List<Order> order, String image_url, UserProfile userProfile, Category category, List<Tag> tags) {
        this.price = price;
        this.description = description;
        this.order = order;
        this.name = name;
        this.image_url = image_url;
        this.userProfile = userProfile;
        this.category = category;
        this.tags = tags;
    }
    
    public Product() {
        
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order>  getOrder() {
        return this.order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }


}
