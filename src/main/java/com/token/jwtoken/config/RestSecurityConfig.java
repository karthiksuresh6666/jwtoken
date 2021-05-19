package com.token.jwtoken.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * The Class WebSecurityConfig.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestSecurityConfig.class);

	private final JwtFilter jwtFilter;

	@Autowired
	public RestSecurityConfig(final JwtFilter jwtFilterParam) {
		this.jwtFilter = jwtFilterParam;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		LOGGER.trace(">>configure()");
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().permitAll();
		http.cors().and().csrf().disable();

		http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		LOGGER.trace("<<configure()");
	}

	@Bean
	public HttpFirewall defaultHttpFirewall() {
		return new DefaultHttpFirewall();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		var configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
