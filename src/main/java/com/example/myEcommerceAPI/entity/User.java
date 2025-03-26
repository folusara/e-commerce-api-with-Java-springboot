package com.example.myEcommerceAPI.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

import com.example.myEcommerceAPI.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
/*
 * 'user' is a reserved keyword in SQL, so we name our table users. If you name it user, you will get a org.h2.jdbc.JdbcSQLSyntaxErrorException. 
 *  See https://docs.microsoft.com/en-us/sql/t-sql/language-elements/reserved-keywords-transact-sql?view=sql-server-ver16 for a list of reserved keywords.
 */ 
@Getter
@Setter
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message =  "Username cannot be blank")
	@NonNull
	@Column(nullable = false, unique = true)
	private String username;

	@NotBlank(message =  "password cannot be blank")
    @NonNull
	@Column(nullable = false)
	private String password;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private UserProfile userProfile;

	


	public User() {
	}

	public User(String username, String password, String name) {
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	// public List<Order> getOrders() {
	// 	return this.orders;
	// }

	// public void setOrders(List<Order> orders) {
	// 	this.orders = orders;
	// }

	public User username(String username) {
		setUsername(username);
		return this;
	}

	// public User orders(List<Order> orders) {
	// 	setOrders(orders);
	// 	return this;
	// }
}