package com.gym.service;

import java.util.List;

import com.gym.dto.MembershipDTO;

public interface MembershipService {

    MembershipDTO addMembership(
            Long memberId,
            MembershipDTO dto);

    List<MembershipDTO> getAllMemberships();

    MembershipDTO getMembershipByMemberId(
            Long memberId);

    MembershipDTO getMyMembership(
            String email);

    MembershipDTO updateMembership(
            Long membershipId,
            MembershipDTO dto);

    void deleteMembership(
            Long membershipId);
}