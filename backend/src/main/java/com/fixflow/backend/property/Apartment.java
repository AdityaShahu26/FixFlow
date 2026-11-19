
package com.fixflow.backend.property;

import com.fixflow.backend.building.Building;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(name = "society_id", nullable = false)
    private Long societyId;

    @Column(name = "flat_number", nullable = false)
    private String flatNumber;

    @Column(name = "floor_number")
    private Integer floorNumber;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Apartment() {
    }

    public Apartment(
            Building building,
            String flatNumber,
            Integer floorNumber) {

        this.building = building;
        this.societyId = building.getSociety().getId();
        this.flatNumber = flatNumber;
        this.floorNumber = floorNumber;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
        this.societyId = building.getSociety().getId();
    }

    public Long getSocietyId() {
        return societyId;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
