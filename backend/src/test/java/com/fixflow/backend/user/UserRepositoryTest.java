package com.fixflow.backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
public class UserRepositoryTest {

    @Autowired
    private  UserRepository userRepository;

    @Test
    void ShouldSaveAndFindUserByEmail(){

        User user = new User("Test Resident",
                "resident@test.com",
                "hashedPassword123",
                "9876543210");

        // Act: Save the user
        userRepository.save(user);

        Optional<User> result =
                userRepository.findByEmailIgnoreCase("resident@test.com");

        // Assert: Verify the result
        assertTrue(result.isPresent());

        assertEquals("Test Resident", result.get().getFullName());
        assertEquals("resident@test.com", result.get().getEmail());

    }

}
