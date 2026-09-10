package com.gym.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.dto.MembershipDTO;
import com.gym.service.MembershipExpiryScheduler;
import com.gym.service.MembershipService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/memberships")
public class MembershipController {

    private final MembershipService membershipService;
    private final MembershipExpiryScheduler membershipExpiryScheduler;

    public MembershipController(
            MembershipService membershipService,
            MembershipExpiryScheduler membershipExpiryScheduler) {

        this.membershipService = membershipService;
        this.membershipExpiryScheduler = membershipExpiryScheduler;
    }

    // =========================
    // ADD MEMBERSHIP
    // ADMIN ONLY
    // =========================
    @PostMapping("/member/{memberId}")
    public MembershipDTO addMembership(
            @PathVariable Long memberId,
            @Valid @RequestBody MembershipDTO membershipDTO) {

        return membershipService.addMembership(
                memberId,
                membershipDTO
        );
    }

    // =========================
    // GET MY MEMBERSHIP
    // MEMBER / ADMIN
    // =========================
    @GetMapping("/me")
    public MembershipDTO getMyMembership(
            Authentication authentication) {

        String email = authentication.getName();

        return membershipService
                .getMyMembership(email);
    }

    // =========================
    // MANUAL EXPIRY CHECK
    // ADMIN ONLY
    // =========================
    @PutMapping("/check-expiry")
    public String checkMembershipExpiry() {

        membershipExpiryScheduler
                .updateExpiredMemberships();

        return "Membership expiry checked successfully";
    }

    // =========================
    // GET ALL MEMBERSHIPS
    // ADMIN ONLY
    // =========================
    @GetMapping
    public List<MembershipDTO> getAllMemberships() {

        return membershipService
                .getAllMemberships();
    }

    // =========================
    // GET MEMBERSHIP BY MEMBER ID
    // ADMIN ONLY
    // =========================
    @GetMapping("/member/{memberId}")
    public MembershipDTO getMembershipByMemberId(
            @PathVariable Long memberId) {

        return membershipService
                .getMembershipByMemberId(memberId);
    }

    // =========================
    // UPDATE MEMBERSHIP
    // ADMIN ONLY
    // =========================
    @PutMapping("/{membershipId}")
    public MembershipDTO updateMembership(
            @PathVariable Long membershipId,
            @Valid @RequestBody MembershipDTO membershipDTO) {

        return membershipService
                .updateMembership(
                        membershipId,
                        membershipDTO
                );
    }

    // =========================
    // DELETE MEMBERSHIP
    // ADMIN ONLY
    // =========================
    @DeleteMapping("/{membershipId}")
    public String deleteMembership(
            @PathVariable Long membershipId) {

        membershipService
                .deleteMembership(membershipId);

        return "Membership deleted successfully";
    }
}