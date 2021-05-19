package com.token.jwtoken.service;

import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.token.jwtoken.config.SecurityConstants;
import com.token.jwtoken.entity.Tokens;
import com.token.jwtoken.entity.User;
import com.token.jwtoken.repository.TokensRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Karthik Suresh
 *
 */
@Service
public class TokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

	@Value("${jwt.token.header}")
	private String tokenHeader;

	@Value("${jwt.token.expiry}")
	private Long tokenExpiry;
	
	@Autowired
	private TokensRepository tokensRepository;

	public Tokens getTokenForUser(final User user) {
		LOGGER.trace(">>getTokenForUser()");
		var token = new Tokens();
		try {
			String secretkey = SecurityConstants.JWT;
			final var issuedDate = new Date();
			final var expirationTime = new Date(issuedDate.getTime() + this.tokenExpiry);
			final var tokenString = Jwts.builder().claim("user_name", user.getUserName())
					.claim("last_name", user.getLastName()).claim("first_name", user.getFirstName())
					.claim("user_id", user.getUserId()).claim("role", user.getRole().toUpperCase(Locale.ENGLISH))
					.setIssuedAt(issuedDate).setExpiration(expirationTime).signWith(SignatureAlgorithm.HS512, secretkey)
					.compact();
			token.setToken(tokenString);
			token = tokensRepository.save(token);
			LOGGER.info("getTokenForUser() Token generated succesfully");
			LOGGER.trace("<<getTokenForUser()");
		} catch (final Exception ex) {
			LOGGER.error("Exception occured in getTokenForUser :{}", ex.getMessage());
		}
		return token;
	}

}
