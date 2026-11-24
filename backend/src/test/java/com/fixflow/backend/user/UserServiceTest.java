
package com.fixflow.backend.user;

import com.fixflow.backend.exception.EmailAlreadyExistsException;
import com.fixflow.backend.user.dto.RegistrationRequest;
import com.fixflow.backend.user.dto.UserResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    void registerUser_shouldSaveUserSuccessfully() {

        // Arrange
        String fullName = "Aditya Shahu";
        String email = "aditya@gmail.com";
        String password = "FixFlow@123";
        String phoneNumber = "9876543210";

        String hashedPassword = "bcrypt-hashed-password";

        when(userRepository.findByEmailIgnoreCase(email))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(password))
                .thenReturn(hashedPassword);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RegistrationRequest request = new RegistrationRequest(
                "Aditya Shahu",
                "aditya@gmail.com",
                "FixFlow@123",
                "9876543210"
        );

        UserResponse savedUser = userService.registerUser(request);

        // Assert
        assertNotNull(savedUser);
        assertEquals(email, savedUser.email());

        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(password);
    }


    @Test
    void registerUser_shouldThrowExceptionWhenEmailAlreadyExists() {

        // Arrange
        String email = "aditya@gmail.com";

        User existingUser = new User(
                "Aditya Shahu",
                email,
                "existing-hashed-password",
                "9876543210"
        );

        RegistrationRequest request = new RegistrationRequest(
                "Another User",
                email,
                "AnotherPassword@123",
                "9999999999"
        );

        when(userRepository.findByEmailIgnoreCase(email))
                .thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(
                EmailAlreadyExistsException.class,
                () -> userService.registerUser(request)
        );

        // Verify
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }



}

