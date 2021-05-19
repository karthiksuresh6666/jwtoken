package com.token.jwtoken.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class JwtConfiguration.
 */
@Component
class JwtConfiguration {

	@Value("${jwt.token.header}")
	private String tokenHeader;

	private String secretkey = SecurityConstants.JWT;

	@Value("${jwt.token.expiry}")
	private Long tokenExpiry;

	public String getTokenHeader() {
		return this.tokenHeader;
	}

	public void setTokenHeader(final String tokenHeaderParam) {
		this.tokenHeader = tokenHeaderParam;
	}

	public String getSecretkey() {
		return this.secretkey;
	}

	public void setSecretkey(final String secretkeyParam) {
		this.secretkey = secretkeyParam;
	}

	public Long getTokenExpiry() {
		return this.tokenExpiry;
	}

	public void setTokenExpiry(final Long tokenExpiryParam) {
		this.tokenExpiry = tokenExpiryParam;
	}
}
