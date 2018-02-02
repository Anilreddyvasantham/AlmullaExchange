package com.amg.exchange.util.impl;

import java.io.Serializable;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.amg.exchange.util.iCypherSecurity;

public class CypherSecurityImpl implements iCypherSecurity,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String encodePassword(String rawSecret, String secretKey) {
		 
		return new ShaPasswordEncoder(256).encodePassword(rawSecret, secretKey);
	}

	public boolean isValidSecret(String encodedSecret, String rawSecret, String secretKey) {

		return new ShaPasswordEncoder(256).isPasswordValid(encodedSecret, rawSecret, secretKey);
	}

	
}
