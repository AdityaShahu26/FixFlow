
package com.fixflow.backend.auth;

import com.fixflow.backend.user.CustomUserDetails;
import com.fixflow.backend.user.dto.LoginRequest;
import com.fixflow.backend.user.dto.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public LoginResult login(LoginRequest request) {

        String normalizedEmail = request.email().trim().toLowerCase();

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                normalizedEmail,
                                request.password()
                        )
                );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        LoginResponse response = new LoginResponse(
                userDetails.getUserId(),
                userDetails.getFullName(),
                userDetails.getUsername()
        );
         return new LoginResult(authentication, response);

    }
    public record LoginResult(
            Authentication authentication,
            LoginResponse response
    ) {}


}
