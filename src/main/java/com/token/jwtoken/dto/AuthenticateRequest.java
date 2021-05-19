/*
 * Copyright (c) 2020 Siemens Technology & Services private limited. All rights
 * reserved. This software is the confidential and proprietary information of
 * Siemens Technology & Services private limited.
 */
package com.token.jwtoken.dto;

import javax.validation.constraints.NotBlank;

/**
 * The Class AuthenticationRequest.
 */
public class AuthenticateRequest {

	@NotBlank(message = "username cannot be null/empty")
    private String userName;

	@NotBlank(message = "password cannot be null/empty")
    private String password;

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public final String getUserName() {
        return this.userName;
    }

    /**
     * Sets the user name.
     *
     * @param userNameParam the new user name
     */
    public final void setUserName(final String userNameParam) {
        this.userName = userNameParam;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public final String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param passWord the new password
     */
    public final void setPassword(final String passWord) {
        this.password = passWord;
    }
}
