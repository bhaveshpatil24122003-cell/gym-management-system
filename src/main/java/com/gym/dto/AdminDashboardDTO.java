package com.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {

    private Long totalMembers;

    private Long activeMemberships;

    private Long expiredMemberships;

    private Double totalRevenue;
}