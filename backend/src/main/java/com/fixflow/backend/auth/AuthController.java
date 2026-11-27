
package com.fixflow.backend.auth;

import com.fixflow.backend.user.CustomUserDetails;
import com.fixflow.backend.user.dto.LoginRequest;
import com.fixflow.backend.user.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final HttpSessionSecurityContextRepository
                securityContextRepository = new HttpSessionSecurityContextRepository();

    public AuthController(
            AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {

        AuthService.LoginResult result = authService.login(request);

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(result.authentication());
        SecurityContextHolder.setContext(context);

        httpRequest.getSession(true);

        httpRequest.changeSessionId();

        securityContextRepository.saveContext(
                context,
                httpRequest,
                httpResponse
        );

        return ResponseEntity.ok(result.response());
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getCurrentUser(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        LoginResponse response = new LoginResponse(
                userDetails.getUserId(),
                userDetails.getFullName(),
                userDetails.getUsername()
        );

        return ResponseEntity.ok(response);
    }
}
