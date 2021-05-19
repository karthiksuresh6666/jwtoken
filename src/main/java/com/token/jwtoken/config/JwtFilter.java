package com.token.jwtoken.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.token.jwtoken.dto.SessionUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * The Class JWTFilter.
 */
@Configuration
public class JwtFilter extends GenericFilterBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

	private static final String AUTHORIZATION_HEADER = "Authorization";

	private String secretkey = SecurityConstants.JWT;

	@Value("${server.port}")
	private String applicationPortNumber;

	@Value("${jwt.token.header}")
	private String tokenHeader;

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain) throws IOException, ServletException {
		LOGGER.trace(">>doFilter()");
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		setRequestInfoforMDC(request);
		setAccessControllHeaders(response);
		final var requestURL = String.valueOf(request.getRequestURL());
		var requestPath = requestURL.substring(requestURL.indexOf(applicationPortNumber));
		if (requestPath.contains("/")) {
			requestPath = requestPath.substring(requestPath.indexOf('/'));
		}
		final String requestMethod = request.getMethod();
		if ("OPTIONS".equalsIgnoreCase(requestMethod)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else if (isWhitelisted(requestPath)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			processServletRequest(servletRequest, servletResponse, filterChain, request);
		}
		LOGGER.trace("<<doFilter()");
	}

	private void processServletRequest(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain, final HttpServletRequest request) throws IOException {
		LOGGER.trace(">>processServletRequest()");
		String authHeader = request.getHeader(AUTHORIZATION_HEADER);
		if (authHeader == null || authHeader.isEmpty()) {
			authHeader = request.getHeader("Token");
		}
		if (authHeader == null || !authHeader.startsWith(tokenHeader)) {
			((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"invalid authorization token.");
		} else {
			try {
				final var token = authHeader.substring(7);
				final var claims = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();

				final String userName = (String) claims.get("user_name");
				final String role = (String) claims.get("role");

				MDC.put("userName", userName);

				SecurityContextHolder.getContext().setAuthentication(doAuthentication(userName, role));

				filterChain.doFilter(servletRequest, servletResponse);

			} catch (final ExpiredJwtException e) {
				LOGGER.error("Token Expired :{}", e.getMessage());
				((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "expired token");
			} catch (final Exception e) {
				LOGGER.error("Invalid Token :{}", e.getMessage());
				((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
			}
		}
		logger.trace("<<processServletRequest()");
	}

	private boolean isWhitelisted(final String requestPath) {
		for (String whitelistedPath : SecurityConstants.getWhitelistedUrls()) {
			if (requestPath.contains(whitelistedPath)) {
				return true;
			}
		}
		return false;
	}

	private void setRequestInfoforMDC(final HttpServletRequest request) {
		LOGGER.trace(">>setRequestInfoforMDC()");
		var url = request.getRequestURL().toString();
		final var queryString = request.getQueryString();
		final String method = request.getMethod();

		final HttpSession session = request.getSession();
		if (session != null) {
			if (queryString != null) {
				url += "?" + queryString;
			}
			MDC.put("url", url);
			MDC.put("method", method);
			MDC.put("sessionID", session.getId());
		}
		LOGGER.trace("<<setRequestInfoforMDC()");
	}

	/**
	 * Sets the access control headers.
	 *
	 * @param response the new access control headers
	 */
	private void setAccessControllHeaders(final HttpServletResponse response) {
		LOGGER.trace(">>setAccessControllHeaders()");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
		LOGGER.trace("<<setAccessControllHeaders()");
	}

	private Authentication doAuthentication(final String userName, final String role) {
		LOGGER.trace(">>doAuthentication()");
		var builder = new StringBuilder();
		builder.append("ROLE_").append(role);
		final List<SimpleGrantedAuthority> authorities = new ArrayList<>(
				Collections.singletonList(new SimpleGrantedAuthority(builder.toString())));
		final var user = new SessionUser(userName, "", true, true, true, true, authorities, userName, role);
		LOGGER.trace("<<doAuthentication()");
		return new UsernamePasswordAuthenticationToken(user, "", authorities);
	}

}
