package com.gym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gym.dto.AdminDashboardDTO;
import com.gym.entity.MemberEntity;
import com.gym.entity.MembershipEntity;
import com.gym.repository.MemberRepository;
import com.gym.repository.MembershipRepository;
import com.gym.util.AppConstants;

@Service
public class AdminDashboardService {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    public AdminDashboardService(
            MemberRepository memberRepository,
            MembershipRepository membershipRepository) {

        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
    }

    public AdminDashboardDTO getDashboardStats() {

        // Get all members
        List<MemberEntity> members =
                memberRepository.findAll();

        // Count only active MEMBER accounts
        long totalMembers = members.stream()
                .filter(member ->
                        !Boolean.TRUE.equals(
                                member.getDeleted()
                        )
                )
                .filter(member ->
                        AppConstants.ROLE_MEMBER
                                .equalsIgnoreCase(
                                        member.getRole()
                                )
                )
                .count();

        // Get all memberships
        List<MembershipEntity> memberships =
                membershipRepository.findAll();

        // Count ACTIVE memberships
        long activeMemberships = memberships.stream()
                .filter(membership ->
                        AppConstants.STATUS_ACTIVE
                                .equalsIgnoreCase(
                                        membership.getStatus()
                                )
                )
                .count();

        // Count EXPIRED memberships
        long expiredMemberships = memberships.stream()
                .filter(membership ->
                        AppConstants.STATUS_EXPIRED
                                .equalsIgnoreCase(
                                        membership.getStatus()
                                )
                )
                .count();

        // Calculate total revenue
        double totalRevenue = memberships.stream()
                .filter(membership ->
                        membership.getPrice() != null
                )
                .mapToDouble(
                        MembershipEntity::getPrice
                )
                .sum();

        return new AdminDashboardDTO(
                totalMembers,
                activeMemberships,
                expiredMemberships,
                totalRevenue
        );
    }
}