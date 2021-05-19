package com.token.jwtoken.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * The Class SessionUser.
 */
public class SessionUser extends User {

	private static final long serialVersionUID = -3531439484732724601L;

	private final String userName;

	private final String role;

	/**
	 * Instantiates a new session user.
	 *
	 * @param username              the user name
	 * @param password              the password
	 * @param enabled               the enabled
	 * @param accountNonExpired     the account non expired
	 * @param credentialsNonExpired the credentials non expired
	 * @param accountNonLocked      the account non locked
	 * @param authorities           the authorities
	 * @param userNameParam         the user name
	 * @param roleParam             the role
	 */
	public SessionUser(final String username, final String password, final boolean enabled,
			final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked,
			final Collection<? extends GrantedAuthority> authorities, final String userNameParam,
			final String roleParam) {

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.userName = userNameParam;
		this.role = roleParam;
	}

	public String getUserName() {
		return userName;
	}

	public String getRole() {
		return role;
	}

}
