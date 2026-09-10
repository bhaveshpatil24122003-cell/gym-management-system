package com.gym.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gym.entity.MembershipEntity;
import com.gym.repository.MembershipRepository;
import com.gym.util.AppConstants;

@Service
public class MembershipExpiryScheduler {

    private final MembershipRepository membershipRepository;

    public MembershipExpiryScheduler(
            MembershipRepository membershipRepository) {

        this.membershipRepository = membershipRepository;
    }

    // Automatically runs every day at 12:00 AM
    @Scheduled(cron = "0 0 0 * * *")
    public void updateExpiredMemberships() {

        LocalDate today = LocalDate.now();

        // Find ACTIVE memberships whose expiry date has passed
        List<MembershipEntity> expiredMemberships =
                membershipRepository
                        .findByExpiryDateBeforeAndStatus(
                                today,
                                AppConstants.STATUS_ACTIVE
                        );

        // Change ACTIVE -> EXPIRED
        for (MembershipEntity membership : expiredMemberships) {

            membership.setStatus(
                    AppConstants.STATUS_EXPIRED
            );
        }

        membershipRepository.saveAll(
                expiredMemberships
        );

        System.out.println(
                "Expired memberships updated: "
                        + expiredMemberships.size()
        );
    }
}