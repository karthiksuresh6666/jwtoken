package com.token.jwtoken.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class TokenEntity.
 */
@Entity
@Table(name = "tokens")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Tokens {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private int tokenId;

	@Column(length=500)
	private String token;

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
