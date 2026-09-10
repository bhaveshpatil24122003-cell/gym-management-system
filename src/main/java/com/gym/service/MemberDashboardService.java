package com.gym.service;

import org.springframework.stereotype.Service;

import com.gym.dto.MemberDTO;
import com.gym.dto.MemberDashboardDTO;
import com.gym.dto.MembershipDTO;

@Service
public class MemberDashboardService {

    private final MemberService memberService;
    private final MembershipService membershipService;

    public MemberDashboardService(
            MemberService memberService,
            MembershipService membershipService) {

        this.memberService = memberService;
        this.membershipService = membershipService;
    }

    public MemberDashboardDTO getDashboard(String email) {

    	    MemberDTO member =
    	            memberService.getMemberByEmail(email);

    	    MembershipDTO membership =
    	            membershipService.getMembershipByMemberId(
    	                    member.getId());

    	    MemberDashboardDTO dashboard =
    	            new MemberDashboardDTO();

    	    dashboard.setProfile(member);
    	    dashboard.setMembership(membership);

    	    return dashboard;
    	}
}