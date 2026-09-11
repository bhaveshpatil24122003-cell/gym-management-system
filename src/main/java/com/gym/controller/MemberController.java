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

import com.gym.dto.MemberDTO;
import com.gym.dto.MemberRequestDTO;
import com.gym.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public MemberDTO addMember(
            @Valid @RequestBody MemberRequestDTO memberDTO) {

        return memberService.addMember(memberDTO);
    }

    @GetMapping
    public List<MemberDTO> getAllMembers() {

        return memberService.getAllMembers();
    }

    @GetMapping("/me")
    public MemberDTO getMyProfile(
            Authentication authentication) {

        String email = authentication.getName();

        return memberService.getMemberByEmail(email);
    }

    @GetMapping("/{id}")
    public MemberDTO getMemberById(
            @PathVariable Long id) {

        return memberService.getMemberById(id);
    }

    @PutMapping("/{id}")
    public MemberDTO updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDTO memberDTO) {

        return memberService.updateMember(id, memberDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteMember(
            @PathVariable Long id) {

        memberService.deleteMember(id);

        return "Member deleted successfully";
    }
}