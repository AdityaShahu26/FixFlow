
package com.fixflow.backend.user.dto;

public record LoginResponse(
        Long id,
        String fullName,
        String email
) {}
