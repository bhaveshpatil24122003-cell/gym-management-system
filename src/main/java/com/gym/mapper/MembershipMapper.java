package com.gym.mapper;

import com.gym.dto.MembershipDTO;
import com.gym.entity.MembershipEntity;

public class MembershipMapper {

    public static MembershipDTO toDTO(
            MembershipEntity membership) {

        MembershipDTO dto = new MembershipDTO();

        dto.setMembershipId(
                membership.getMembershipId());

        dto.setSubscriptionPlan(
                membership.getSubscriptionPlan());

        dto.setPrice(membership.getPrice());

        dto.setRegistrationDate(
                membership.getRegistrationDate());

        dto.setExpiryDate(
                membership.getExpiryDate());

        dto.setPaymentMode(
                membership.getPaymentMode());

        dto.setStatus(
                membership.getStatus());

        if (membership.getMember() != null) {
            dto.setMemberId(
                    membership.getMember().getId());
        }

        return dto;
    }
}