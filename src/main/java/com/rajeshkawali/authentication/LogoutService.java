package com.rajeshkawali.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.rajeshkawali.constant.CustomerConstants;
import com.rajeshkawali.repository.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Rajesh_Kawali
 * 
 */
@Service
public class LogoutService implements LogoutHandler {

	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String jwt;
		if (authHeader == null || !authHeader.startsWith(CustomerConstants.BEARER_)) {
			return;
		}
		jwt = authHeader.substring(7);
		var storedToken = tokenRepository.findByToken(jwt).orElse(null);
		if (storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
			SecurityContextHolder.clearContext();
		}
	}
}