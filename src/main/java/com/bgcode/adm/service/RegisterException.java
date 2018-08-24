package com.bgcode.adm.service;

import org.springframework.security.core.AuthenticationException;

public class RegisterException extends AuthenticationException {

	
	private static final long serialVersionUID = 1L;

	public RegisterException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
