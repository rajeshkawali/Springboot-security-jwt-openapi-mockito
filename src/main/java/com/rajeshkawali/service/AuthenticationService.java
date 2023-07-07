package com.rajeshkawali.service;

import java.io.IOException;

import com.rajeshkawali.dto.AuthenticationRequest;
import com.rajeshkawali.dto.AuthenticationResponse;
import com.rajeshkawali.dto.RegisterRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Rajesh_Kawali
 * 
 */
public interface AuthenticationService {

	public AuthenticationResponse register(RegisterRequest request);

	public AuthenticationResponse authenticate(AuthenticationRequest request);

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
