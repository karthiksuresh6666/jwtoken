package com.token.jwtoken.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.jwtoken.dto.AuthenticateRequest;
import com.token.jwtoken.dto.RestResponse;
import com.token.jwtoken.repository.UserRepository;
import com.token.jwtoken.service.TokenService;

/**
 * @author Karthik Suresh
 *
 */
@RestController
@RequestMapping("/v1/jwt/")
public class TokenedController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenedController.class);

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("authenticated")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<RestResponse> endPoint() {
		LOGGER.trace(">>endPoint()");
		var restResponse = new RestResponse();
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@PostMapping("token")
	public ResponseEntity<RestResponse> authenticateUser(
			@Valid @RequestBody final AuthenticateRequest authenticateRequest) {
		LOGGER.trace(">>authenticateUser()");
		var restResponse = new RestResponse();
		var user = userRepository.findByUserName(authenticateRequest.getUserName());
		if (null != user) {
			// Check that an unencrypted password matches one that has
			// previously been hashed
			if (!BCrypt.checkpw(authenticateRequest.getPassword(), user.getPassword())) {
				restResponse.setData(tokenService.getTokenForUser(user));
			}else {
				LOGGER.error("authenticateUser() Invalid user credentials");
			}
		} else {
			LOGGER.error("authenticateUser() Invalid user credentials");
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.trace("<<authenticateUser()");
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

}
