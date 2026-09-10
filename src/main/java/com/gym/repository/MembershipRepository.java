package com.gym.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym.entity.MembershipEntity;

@Repository
public interface MembershipRepository
        extends JpaRepository<MembershipEntity, Long> {

    Optional<MembershipEntity> findByMemberId(Long memberId);

    List<MembershipEntity> findByExpiryDateBeforeAndStatus(
            LocalDate date,
            String status
    );
}