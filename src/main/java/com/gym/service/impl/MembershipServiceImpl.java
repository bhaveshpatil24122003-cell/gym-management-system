package com.gym.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gym.dto.MembershipDTO;
import com.gym.entity.MemberEntity;
import com.gym.entity.MembershipEntity;
import com.gym.exception.DuplicateResourceException;
import com.gym.exception.ResourceNotFoundException;
import com.gym.mapper.MembershipMapper;
import com.gym.repository.MemberRepository;
import com.gym.repository.MembershipRepository;
import com.gym.service.MembershipService;
import com.gym.util.AppConstants;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;

    public MembershipServiceImpl(
            MembershipRepository membershipRepository,
            MemberRepository memberRepository) {

        this.membershipRepository = membershipRepository;
        this.memberRepository = memberRepository;
    }


    // =========================
    // ADD MEMBERSHIP
    // =========================
    @Override
    public MembershipDTO addMembership(
            Long memberId,
            MembershipDTO dto) {

        MemberEntity member = memberRepository
                .findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + memberId
                        )
                );

        // Same member cannot have two memberships
        if (membershipRepository
                .findByMemberId(memberId)
                .isPresent()) {

            throw new DuplicateResourceException(
                    "Membership already exists for this member"
            );
        }

        MembershipEntity membership =
                new MembershipEntity();

        membership.setSubscriptionPlan(
                dto.getSubscriptionPlan());

        membership.setPrice(
                dto.getPrice());

        membership.setPaymentMode(
                dto.getPaymentMode());

        LocalDate registrationDate =
                LocalDate.now();

        membership.setRegistrationDate(
                registrationDate);

        membership.setExpiryDate(
                calculateExpiryDate(
                        registrationDate,
                        dto.getSubscriptionPlan()
                )
        );

        membership.setStatus(
                AppConstants.STATUS_ACTIVE
        );

        membership.setMember(member);

        MembershipEntity savedMembership =
                membershipRepository.save(membership);

        return MembershipMapper.toDTO(savedMembership);
    }


    // =========================
    // GET ALL MEMBERSHIPS
    // =========================
    @Override
    public List<MembershipDTO> getAllMemberships() {

        return membershipRepository
                .findAll()
                .stream()
                .map(MembershipMapper::toDTO)
                .toList();
    }


    // =========================
    // GET MEMBERSHIP BY MEMBER ID
    // =========================
    @Override
    public MembershipDTO getMembershipByMemberId(
            Long memberId) {

        MembershipEntity membership =
                membershipRepository
                        .findByMemberId(memberId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership not found for member id: "
                                                + memberId
                                )
                        );

        return MembershipMapper.toDTO(membership);
    }


    // =========================
    // GET LOGGED-IN MEMBER
    // MEMBERSHIP
    // =========================
    @Override
    public MembershipDTO getMyMembership(
            String email) {

        MemberEntity member =
                memberRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found"
                                )
                        );

        if (Boolean.TRUE.equals(
                member.getDeleted())) {

            throw new ResourceNotFoundException(
                    "Member account is deleted"
            );
        }

        MembershipEntity membership =
                membershipRepository
                        .findByMemberId(member.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership not found for this member"
                                )
                        );

        return MembershipMapper.toDTO(membership);
    }


    // =========================
    // UPDATE MEMBERSHIP
    // =========================
    @Override
    public MembershipDTO updateMembership(
            Long membershipId,
            MembershipDTO dto) {

        MembershipEntity membership =
                membershipRepository
                        .findById(membershipId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership not found with id: "
                                                + membershipId
                                )
                        );

        membership.setSubscriptionPlan(
                dto.getSubscriptionPlan());

        membership.setPrice(
                dto.getPrice());

        membership.setPaymentMode(
                dto.getPaymentMode());

        membership.setExpiryDate(
                calculateExpiryDate(
                        membership.getRegistrationDate(),
                        dto.getSubscriptionPlan()
                )
        );

        membership.setStatus(
                AppConstants.STATUS_ACTIVE
        );

        MembershipEntity updatedMembership =
                membershipRepository.save(membership);

        return MembershipMapper.toDTO(
                updatedMembership
        );
    }


    // =========================
    // DELETE MEMBERSHIP
    // =========================
    @Override
    public void deleteMembership(
            Long membershipId) {

        MembershipEntity membership =
                membershipRepository
                        .findById(membershipId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership not found with id: "
                                                + membershipId
                                )
                        );

        membershipRepository.delete(membership);
    }


    // =========================
    // CALCULATE EXPIRY DATE
    // =========================
    private LocalDate calculateExpiryDate(
            LocalDate registrationDate,
            String plan) {

        if (AppConstants.PLAN_1_MONTH
                .equalsIgnoreCase(plan)) {

            return registrationDate.plusMonths(1);

        } else if (AppConstants.PLAN_3_MONTHS
                .equalsIgnoreCase(plan)) {

            return registrationDate.plusMonths(3);

        } else if (AppConstants.PLAN_6_MONTHS
                .equalsIgnoreCase(plan)) {

            return registrationDate.plusMonths(6);

        } else if (AppConstants.PLAN_1_YEAR
                .equalsIgnoreCase(plan)) {

            return registrationDate.plusYears(1);

        } else {

            throw new IllegalArgumentException(
                    "Invalid subscription plan"
            );
        }
    }
}