package com.gym.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gym.entity.MemberEntity;
import com.gym.repository.MemberRepository;
import com.gym.util.AppConstants;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(
            MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // Find member using email
        MemberEntity member =
                memberRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Member not found with email: "
                                                + email
                                )
                        );

        // Soft deleted member cannot login
        if (Boolean.TRUE.equals(
                member.getDeleted())) {

            throw new UsernameNotFoundException(
                    "Member account is deleted"
            );
        }

        // Role safety for old database records
        String role = member.getRole();

        if (role == null || role.isBlank()) {

            role = AppConstants.ROLE_MEMBER;
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(role)
                .build();
    }
}