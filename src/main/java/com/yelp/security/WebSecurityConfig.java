package com.yelp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// disable caching
		http.headers().cacheControl();

		// @formatter:off
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/sign_up", "/about", "/contact").permitAll()
				.antMatchers(HttpMethod.POST, "/sign_in").permitAll()
				.anyRequest().authenticated()
				.and()
			// We filter the api/login requests
			.addFilterBefore(new JWTLoginFilter("/sign_in", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
			// And filter other requests to check the presence of JWT in header
			.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		// @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Create a default account for testing
		// @formatter:off
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password("password")
			.roles("ADMIN");
		// @formatter:on
	}

}
