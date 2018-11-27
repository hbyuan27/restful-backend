package com.hbyuan.demo.config.authentication;

public enum AuthenticationMode {

	BASIC, LDAP;

	public static AuthenticationMode resolve(String name) {
		for (AuthenticationMode mode : AuthenticationMode.values()) {
			if (mode.toString().equalsIgnoreCase(name)) {
				return mode;
			}
		}
		// default authentication mode
		return BASIC;
	}

}
