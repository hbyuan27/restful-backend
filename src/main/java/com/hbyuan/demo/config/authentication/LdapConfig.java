package com.hbyuan.demo.config.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
public class LdapConfig {

	@Value("${spring.ldap.url}")
	private String url;

	@Value("${spring.ldap.base.dn}")
	private String baseDn;

	@Value("${spring.ldap.user.dn.pattern}")
	private String dnPattern;

	@Value("${spring.ldap.manager.dn}")
	private String username;

	@Value("${spring.ldap.manager.password}")
	private String password;

	@Value("${spring.ldap.search.base}")
	private String searchBase;

	@Value("${spring.ldap.password.compare.enabled}")
	private boolean compareEnabled;

	@Value("${spring.ldap.password.attribute}")
	private String pwdAttribute;

	@Value("${spring.ldap.password.encoder}")
	private String pwdEncoder;

	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = null;
		PasswordEncodeMode mode = PasswordEncodeMode.resolve(pwdEncoder);
		switch (mode) {
			case SHA :
				encoder = new LdapShaPasswordEncoder();
				break;
			case PLAIN_TEXT :
			default :
				encoder = new PlaintextPasswordEncoder();
				break;
		}
		return encoder;
	}

	public String url() {
		if (url.endsWith("/")) {
			return url + baseDn;
		} else {
			return url + "/" + baseDn;
		}
	}

	public String dnPattern() {
		return dnPattern;
	}

	public String searchBase() {
		return searchBase;
	}

	public String username() {
		return (username.isEmpty()) ? null : username;
	}

	public String password() {
		return (password.isEmpty()) ? null : password;
	}

	public String pwdAttribute() {
		// default passwordCompare().passwordAttribute() - "userPassword"
		return (pwdAttribute.isEmpty()) ? "userPassword" : pwdAttribute;
	}

	public boolean isCompareEnabled() {
		return compareEnabled;
	}

}
