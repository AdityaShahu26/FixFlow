package com.fixflow.backend.building;


import com.fixflow.backend.society.Society;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Optional;

@Entity
@Table(
        name = "buildings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_buildings_id_society",
                        columnNames = {"id", "society_id"}
                )
        }
)
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Building() {
    }

    public Building(Society society, String name) {
        this.society = society;
        this.name = name;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }


    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
