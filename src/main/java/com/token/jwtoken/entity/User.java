package com.token.jwtoken.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Karthik Suresh
 *
 */
@Entity
@Table(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "first_name", length = 64)
	private String firstName;

	@Column(name = "last_name", length = 64)
	private String lastName;

	@Column(name = "user_name", length = 64, unique = true)
	private String userName;

	private String password;

	private String role;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encryptPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private String encryptPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}

}
