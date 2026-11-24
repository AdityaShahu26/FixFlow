
package com.fixflow.backend.user.dto;

public record UserResponse(

        Long id,
        String fullName,
        String email,
        String phoneNumber

) {
}
