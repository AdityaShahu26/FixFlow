package com.fixflow.backend.society;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocietyRepository
        extends JpaRepository<Society, Long> {

    Optional<Society> findByRegistrationNumber(String registrationNumber);
}
