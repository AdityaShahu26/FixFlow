
package com.fixflow.backend.property;

import com.fixflow.backend.membership.SocietyMembership;
import java.time.Instant;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "membership_apartments")
public class MembershipApartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "society_id", nullable = false)
    private Long societyId;

    @Column(name = "membership_id", nullable = false)
    private Long membershipId;

    @Column(name = "apartment_id", nullable = false)
    private Long apartmentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(
                    name = "membership_id",
                    referencedColumnName = "id",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "society_id",
                    referencedColumnName = "society_id",
                    insertable = false,
                    updatable = false
            )
    })
    private SocietyMembership membership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(
                    name = "apartment_id",
                    referencedColumnName = "id",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "society_id",
                    referencedColumnName = "society_id",
                    insertable = false,
                    updatable = false
            )
    })
    private Apartment apartment;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApartmentRelationship relationship;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_until")
    private Instant validUntil;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public MembershipApartment() {
    }

    public MembershipApartment(
            SocietyMembership membership,
            Apartment apartment,
            ApartmentRelationship relationship,
            Instant validFrom) {

        this.membership = membership;
        this.apartment = apartment;

        this.membershipId = membership.getId();
        this.apartmentId = apartment.getId();
        this.societyId = membership.getSociety().getId();

        this.relationship = relationship;
        this.validFrom = validFrom;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getSocietyId() {
        return societyId;
    }

    public SocietyMembership getMembership() {
        return membership;
    }

    public void setMembership(SocietyMembership membership) {
        this.membership = membership;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public ApartmentRelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(ApartmentRelationship relationship) {
        this.relationship = relationship;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    public Long getMembershipId() {
        return membershipId;
    }

    public Long getApartmentId() {
        return apartmentId;
    }
}
