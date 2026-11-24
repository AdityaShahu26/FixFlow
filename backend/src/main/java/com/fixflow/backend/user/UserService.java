package com.fixflow.backend.user;

import com.fixflow.backend.exception.EmailAlreadyExistsException;
import com.fixflow.backend.user.dto.RegistrationRequest;
import com.fixflow.backend.user.dto.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(RegistrationRequest request){

        // 1. Normalize the email
        String normalizedEmail = request.email().trim().toLowerCase();


      // 2. Check whether email already exists
        if(userRepository.findByEmailIgnoreCase(normalizedEmail).isPresent()){
            throw new EmailAlreadyExistsException(
                    "Email already registered"
            );

        }

        // 3. Hash the password
        String hashedPassword = passwordEncoder.encode(request.password());


        // 4. Create a new User object
        User user = new User(
                request.fullName().trim(),
                normalizedEmail,
                hashedPassword,
                request.phoneNumber()
        );

        // 5. Save the user in PostgreSQL
        User savedUser =  userRepository.save(user);

        return  new UserResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber());
    }


}





