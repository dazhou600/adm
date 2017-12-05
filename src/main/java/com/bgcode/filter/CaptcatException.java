package com.bgcode.filter;

import org.springframework.security.core.AuthenticationException;


public class CaptcatException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CaptcatException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
