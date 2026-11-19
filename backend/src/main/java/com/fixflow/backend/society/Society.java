package com.fixflow.backend.society;
import  jakarta.persistence.*;
import java.time.Instant;



@Entity
@Table(name = "societies")


public class Society {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(name = "registration_number")
        private String registrationNumber;

        @Column(nullable = false)
        private String address;

        @Column(nullable = false)
        private String city;

        @Column(nullable = false)
        private String state;

        @Column(name = "postal_code", nullable = false)
        private String postalCode;

        @Column(name = "created_at", nullable = false)
        private Instant createdAt;

        @Column(name = "updated_at", nullable = false)
        private Instant updatedAt;

    // No-argument constructor
    public Society() {
    }

    // Parameterized constructor
    public Society(
            String name,
            String registrationNumber,
            String address,
            String city,
            String state,
            String postalCode) {

        this.name = name;
        this.registrationNumber = registrationNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
