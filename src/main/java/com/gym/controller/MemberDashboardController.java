package com.gym.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.dto.MemberDashboardDTO;
import com.gym.service.MemberDashboardService;

@RestController
@RequestMapping("/dashboard")
public class MemberDashboardController {

    private final MemberDashboardService memberDashboardService;

    public MemberDashboardController(
            MemberDashboardService memberDashboardService) {

        this.memberDashboardService = memberDashboardService;
    }

    // =========================
    // GET LOGGED-IN MEMBER DASHBOARD
    // =========================
    @GetMapping("/me")
    public MemberDashboardDTO getMyDashboard(
            Authentication authentication) {

        String email = authentication.getName();

        return memberDashboardService.getDashboard(email);
    }
}