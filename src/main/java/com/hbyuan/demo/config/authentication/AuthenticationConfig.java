package com.hbyuan.demo.config.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {

	@Value("${authentication.mode}")
	private String mode;

	@Autowired
	private LdapConfig ldapConfig;

	public AuthenticationMode getMode() {
		return AuthenticationMode.resolve(mode);
	}

	public LdapConfig getLdapConfig() {
		return ldapConfig;
	}

}
