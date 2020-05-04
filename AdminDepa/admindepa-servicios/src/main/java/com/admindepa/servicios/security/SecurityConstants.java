package com.admindepa.servicios.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
	public static String JWT_SECRET;
	public static long JWT_EXPIRES_IN; // 10 days
	public static String TOKEN_PREFIX;
	public static String JWT_HEADER_AUTH;
	public static String JWT_TYPE;

	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		SecurityConstants.JWT_SECRET = secret;
	}

	@Value("${jwt.expires.in}")
	public void setExpiresIn(long expiresIn) {
		SecurityConstants.JWT_EXPIRES_IN = expiresIn;
	}

	@Value("${jwt.header.auth}")
	public void setHeaderAuth(String headerAuth) {
		SecurityConstants.JWT_HEADER_AUTH = headerAuth;
	}

	@Value("${jwt.token.prefix}")
	public void setTokenPrefix(String tokenPrefix) {
		SecurityConstants.TOKEN_PREFIX = tokenPrefix;
	}
	
	@Value("${jwt.token.type}")
	public void setTokenType(String tokenType) {
		SecurityConstants.JWT_TYPE = tokenType;
	}

}
