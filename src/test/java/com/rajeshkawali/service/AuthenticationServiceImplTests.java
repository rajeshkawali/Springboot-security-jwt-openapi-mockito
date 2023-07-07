package com.rajeshkawali.service;
import static com.rajeshkawali.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rajeshkawali.authentication.JwtService;
import com.rajeshkawali.dto.AuthenticationRequest;
import com.rajeshkawali.dto.AuthenticationResponse;
import com.rajeshkawali.dto.RegisterRequest;
import com.rajeshkawali.entity.Role;
import com.rajeshkawali.entity.Token;
import com.rajeshkawali.entity.TokenType;
import com.rajeshkawali.entity.User;
import com.rajeshkawali.repository.TokenRepository;
import com.rajeshkawali.repository.UserRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Rajesh_Kawali
 * 
 */
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
public class AuthenticationServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest("Rajesh", "Kawali", "rajesh@mail.com", "password", Role.USER);
        User savedUser = User.builder()
        		.id(1)
        		.firstname("User")
				.lastname("User")
				.email("user@mail.com")
				.password("password")
				.role(USER).build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("jwt_token");
        Mockito.when(jwtService.generateRefreshToken(Mockito.any(User.class))).thenReturn("refresh_token");

        AuthenticationResponse response = authenticationService.register(request);

        assertEquals("jwt_token", response.getAccessToken());
        assertEquals("refresh_token", response.getRefreshToken());
        verify(userRepository).save(Mockito.any(User.class));
        verify(jwtService).generateToken(Mockito.any(User.class));
        verify(jwtService).generateRefreshToken(Mockito.any(User.class));
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest("user@mail.com", "password");
        User user = User.builder()
        		.id(1)
        		.firstname("User")
				.lastname("User")
				.email("user@mail.com")
				.password("password")
				.role(USER).build();
        
        Token token = Token.builder().expired(false).revoked(false).token("jwt_token").tokenType(TokenType.BEARER).build();
        List<Token> tokenList = List.of(token);
        
        Mockito.when(userRepository.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("jwt_token");
        Mockito.when(jwtService.generateRefreshToken(Mockito.any(User.class))).thenReturn("refresh_token");
        Mockito.when(tokenRepository.findAllValidTokenByUser(Mockito.anyInt())).thenReturn(tokenList);

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertEquals("jwt_token", response.getAccessToken());
        assertEquals("refresh_token", response.getRefreshToken());
        verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("user@mail.com");
        verify(jwtService).generateToken(Mockito.any(User.class));
        verify(jwtService).generateRefreshToken(Mockito.any(User.class));
        verify(tokenRepository).findAllValidTokenByUser(Mockito.anyInt());
        verify(tokenRepository).saveAll(Mockito.anyList());
    }

    @Test
    public void testRefreshToken() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        User user = User.builder()
        		.firstname("User")
				.lastname("User")
				.email("user@mail.com")
				.password("password")
				.role(USER).build();

        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refresh_token");
        Mockito.when(jwtService.extractUsername("refresh_token")).thenReturn("user@mail.com");
        Mockito.when(userRepository.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
        Mockito.when(jwtService.isTokenValid("refresh_token", user)).thenReturn(true);
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("new_access_token");

        ServletOutputStream outputStream = Mockito.mock(ServletOutputStream.class);
        Mockito.when(response.getOutputStream()).thenReturn(outputStream);

        authenticationService.refreshToken(request, response);

        verify(userRepository).findByEmail("user@mail.com");
        verify(jwtService).isTokenValid("refresh_token", user);
        verify(jwtService).generateToken(Mockito.any(User.class));
        verify(response).getOutputStream();
    }
    
    @Test
    public void testRefreshTokenNull() throws IOException {
    	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        authenticationService.refreshToken(request, response);
        verify(request).getHeader(HttpHeaders.AUTHORIZATION);
    }
}