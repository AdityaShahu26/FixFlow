
package com.fixflow.backend.auth;

import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import com.fixflow.backend.user.CustomUserDetails;
import com.fixflow.backend.user.User;
import com.fixflow.backend.user.dto.LoginRequest;
import com.fixflow.backend.user.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.ConditionalAuthorizationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails userDetails;

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest(
                "rahul@example.com",
                "SecurePass123"
        );




        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(userDetails);

        when(userDetails.getUserId()).thenReturn(1L);
        when(userDetails.getFullName()).thenReturn("Rahul Sharma");
        when(userDetails.getUsername()).thenReturn("rahul@example.com");

        AuthService.LoginResult result =
                authService.login(request);

        LoginResponse response = result.response();

        assertEquals(1L, response.id());
        assertEquals("Rahul Sharma", response.fullName());
        assertEquals("rahul@example.com", response.email());

        verify(authenticationManager).authenticate(any());
    }

    @Test
    void shouldThrowExceptionWhenCredentialsAreInvalid(){
        LoginRequest request = new LoginRequest(
                "rahul@example.com",
                "WrongPassword123"
        );

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));


        assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );
    }

    @Test
    void shouldNormalizeEmailBeforeAuthentication() {

        LoginRequest request = new LoginRequest(
                "  RAHUL@EXAMPLE.COM  ",
                "SecurePass123"
        );

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(userDetails);

        when(userDetails.getUserId()).thenReturn(1L);
        when(userDetails.getFullName()).thenReturn("Rahul Sharma");
        when(userDetails.getUsername()).thenReturn("rahul@example.com");

        // Capture the argument passed to authenticate()
        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        authService.login(request);

        verify(authenticationManager).authenticate(captor.capture());

        String actualEmail = captor.getValue().getPrincipal().toString();

        assertEquals("rahul@example.com", actualEmail);
    }
}
