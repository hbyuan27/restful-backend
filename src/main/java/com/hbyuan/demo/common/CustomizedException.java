package com.hbyuan.demo.common;

public class CustomizedException extends RuntimeException {

	private static final long serialVersionUID = 122256749536054259L;

	private int code;

	public CustomizedException(int code, String message) {
		super(message);
		this.code = code;
	}

	public CustomizedException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}