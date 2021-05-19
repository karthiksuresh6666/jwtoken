package com.token.jwtoken.config;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.token.jwtoken.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The Class JwtEncoder.
 */
@Component
public class JwtEncoder {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtEncoder.class);

	private final JwtConfiguration jwtConfiguration;

	public String encode(final User user) {
		LOGGER.trace(">>encode()");
		final var issuedDate = new Date();
		final var expirationTime = new Date(issuedDate.getTime() + this.jwtConfiguration.getTokenExpiry());
		final var tokenString = Jwts.builder().claim("user_name", user.getUserName())
				.claim("last_name", user.getLastName()).claim("first_name", user.getFirstName())
				.claim("uId", user.getUserId()).claim("role", user.getRole().toUpperCase()).setIssuedAt(issuedDate)
				.setExpiration(expirationTime).signWith(SignatureAlgorithm.HS512, this.jwtConfiguration.getSecretkey())
				.compact();
		LOGGER.trace("<<encode()");
		var tokenStringBuilder = new StringBuilder();
		tokenStringBuilder.append(this.jwtConfiguration.getTokenHeader()).append(' ').append(tokenString);
		return tokenStringBuilder.toString();
	}

	public JwtEncoder(final JwtConfiguration jwtConfig) {
		this.jwtConfiguration = jwtConfig;
	}

}