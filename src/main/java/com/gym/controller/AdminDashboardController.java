package com.gym.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.dto.AdminDashboardDTO;
import com.gym.service.AdminDashboardService;

@RestController
@RequestMapping("/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(
            AdminDashboardService adminDashboardService) {

        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping("/admin")
    public AdminDashboardDTO getAdminDashboard() {

        return adminDashboardService.getDashboardStats();
    }
}