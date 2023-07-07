package com.rajeshkawali.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rajeshkawali.dto.AuthenticationRequest;
import com.rajeshkawali.dto.AuthenticationResponse;
import com.rajeshkawali.dto.RegisterRequest;
import com.rajeshkawali.entity.Role;
import com.rajeshkawali.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Rajesh_Kawali
 * 
 */
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
public class AuthenticationControllerTests {

	@MockBean
	private AuthenticationService authenticationService;

	@InjectMocks
	private AuthenticationController authenticationController;

	@Test
	public void testRegister() throws Exception {
		RegisterRequest registerRequest = RegisterRequest.builder()
				.email("admin@gmail.com")
				.firstName("Admin")
				.lastName("Admin")
				.password("password")
				.role(Role.ADMIN)
				.build();
		AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
				.accessToken("xyz")
				.refreshToken("abc")
				.build();
		Mockito.when(authenticationService.register(Mockito.any(RegisterRequest.class)))
				.thenReturn(authenticationResponse);
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(registerRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testAuthenticate() throws Exception {
		AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
				.email("admin@gmail.com")
				.password("password")
				.build();
		AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
				.accessToken("xyz")
				.refreshToken("abc")
				.build();
		Mockito.when(authenticationService.authenticate(Mockito.any(AuthenticationRequest.class)))
				.thenReturn(authenticationResponse);
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.authenticate(authenticationRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testRefreshToken() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        authenticationController.refreshToken(request, response);
        verify(authenticationService).refreshToken(request, response);
	}

}