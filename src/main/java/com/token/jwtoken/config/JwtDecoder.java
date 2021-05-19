package com.token.jwtoken.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * The Class JwtDecoder.
 */
@Component
public class JwtDecoder {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtDecoder.class);

	private final JwtConfiguration jwtConfiguration;

	/**
	 * Decode.
	 *
	 * @param token the token
	 * @return the claims
	 */
	public Claims decode(final String token) {
		LOGGER.trace(">>decode()");
		return Jwts.parser().setSigningKey(this.jwtConfiguration.getSecretkey()).parseClaimsJws(token).getBody();
	}

	public JwtDecoder(final JwtConfiguration jwtConfig) {
		this.jwtConfiguration = jwtConfig;
	}
	
}
