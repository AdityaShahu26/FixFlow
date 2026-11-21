package com.fixflow.backend.membership;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocietyMembershipRepository extends JpaRepository<SocietyMembership , Long> {

    List<SocietyMembership> findBySocietyIdAndRoleAndStatus(
            Long societyId,
            MembershipRole role,
            MembershipStatus status
    );

    boolean existsByUserIdAndSocietyId(Long userId, Long societyId);

}

