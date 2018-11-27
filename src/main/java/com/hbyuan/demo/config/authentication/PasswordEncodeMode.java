package com.hbyuan.demo.config.authentication;

public enum PasswordEncodeMode {

	PLAIN_TEXT, SHA;

	public static PasswordEncodeMode resolve(String name) {
		for (PasswordEncodeMode mode : PasswordEncodeMode.values()) {
			if (mode.toString().equalsIgnoreCase(name)) {
				return mode;
			}
		}
		// default encoder
		return PLAIN_TEXT;
	}

}
