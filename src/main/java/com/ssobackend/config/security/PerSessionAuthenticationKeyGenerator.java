package com.ssobackend.config.security;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.stereotype.Component;

/**
 * Key generator taking into account the client id, scope, resource ids, username and UUID. This ensures that
 * every time an authentication token is requested, it is unique and same authentication token isn't shared across
 * more than one requests.
 * 
 * @author Tahir Naqash
 * */

@Component
public class PerSessionAuthenticationKeyGenerator implements AuthenticationKeyGenerator{

	private static final String CLIENT_ID = "client_id";
	private static final String SCOPE = "scope";
	private static final String USERNAME = "username";
	private static final String UUID_KEY = "uuid";

	@Override
	public String extractKey(OAuth2Authentication authentication) {
		Map<String, String> values = new LinkedHashMap<String, String>();
		OAuth2Request authorizationRequest = authentication.getOAuth2Request();
		if (!authentication.isClientOnly()) {
			values.put(USERNAME, authentication.getName());
		}
		values.put(CLIENT_ID, authorizationRequest.getClientId());
		if (authorizationRequest.getScope() != null) {
			values.put(SCOPE, OAuth2Utils.formatParameterList(authorizationRequest.getScope()));
		}

		Map<String, Serializable> extentions = authorizationRequest.getExtensions();
		String uuid = null;
		if (extentions == null) {
			extentions = new HashMap<String, Serializable>(1);
			uuid = UUID.randomUUID().toString();
			extentions.put(UUID_KEY, uuid);
		}
		else {
			uuid = (String) extentions.get(UUID_KEY);
			if (uuid == null) {
				uuid = UUID.randomUUID().toString();
				extentions.put(UUID_KEY, uuid);
			}
		}
		values.put(UUID_KEY, uuid);

		return generateKey(values);
	}

	protected String generateKey(Map<String, String> values) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		} catch (NoSuchAlgorithmException nsae) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", uee);
		}
	}
}