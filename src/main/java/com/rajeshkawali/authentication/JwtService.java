package com.rajeshkawali.authentication;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Rajesh_Kawali
 * 
 */
public interface JwtService {

	public boolean isTokenValid(String token, UserDetails userDetails);

	public String extractUsername(String token);

	public String generateToken(UserDetails userDetails);

	public String generateRefreshToken(UserDetails userDetails);
}
