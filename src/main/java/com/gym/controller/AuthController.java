package com.gym.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.dto.LoginRequest;
import com.gym.dto.LoginResponse;
import com.gym.entity.MemberEntity;
import com.gym.repository.MemberRepository;
import com.gym.security.CustomUserDetailsService;
import com.gym.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final MemberRepository memberRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailsService userDetailsService,
            MemberRepository memberRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()) );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        loginRequest.getEmail());

        String token =
                jwtService.generateToken(userDetails);

        MemberEntity member =
                memberRepository.findByEmail(loginRequest.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException("Member not found"));

        return new LoginResponse(
                token,
                member.getRole(),
                "Login successful");
    }
}