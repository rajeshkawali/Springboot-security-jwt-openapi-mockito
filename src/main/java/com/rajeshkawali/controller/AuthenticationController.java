package com.rajeshkawali.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajeshkawali.dto.AuthenticationRequest;
import com.rajeshkawali.dto.AuthenticationResponse;
import com.rajeshkawali.dto.RegisterRequest;
import com.rajeshkawali.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Rajesh_Kawali
 * 
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthenticationController {
	
	public static final String CLASS_NAME = AuthenticationController.class.getName();

	@Autowired
	private AuthenticationService service;

	@Operation(summary = "Register", description = "This api used to register user to access secured api's")
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		String _function = ".register";
		log.info(CLASS_NAME + _function + "::ENTER");
		return ResponseEntity.ok(service.register(request));
	}

	@Operation(summary = "Authenticate", description = "This api used to authenticate user to access secured api's")
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		String _function = ".authenticate";
		log.info(CLASS_NAME + _function + "::ENTER");
		return ResponseEntity.ok(service.authenticate(request));
	}

	@Operation(summary = "Refresh token", description = "This api used to refresh the token")
	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String _function = ".refreshToken";
		log.info(CLASS_NAME + _function + "::ENTER");
		service.refreshToken(request, response);
	}

}
