package com.token.jwtoken.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karthik Suresh
 *
 */
public class SecurityConstants {

	// JWT
	public static final String JWT = "Authentication for the Rest End Points";
	
	private static final List<String> WHITELISTED_URLS = new ArrayList<>();

	static {
		// do not require authentication for procuring the token
		WHITELISTED_URLS.add("/v1/jwt/token");
	}

	public static List<String> getWhitelistedUrls() {
		return WHITELISTED_URLS.stream().collect(Collectors.toList());
	}

}
