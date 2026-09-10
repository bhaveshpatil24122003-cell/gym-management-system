package com.gym.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gym.dto.MemberDTO;
import com.gym.dto.MemberRequestDTO;
import com.gym.entity.MemberEntity;
import com.gym.exception.DuplicateResourceException;
import com.gym.exception.ResourceNotFoundException;
import com.gym.mapper.MemberMapper;
import com.gym.repository.MemberRepository;
import com.gym.service.MemberService;
import com.gym.util.AppConstants;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder) {

        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // ADD MEMBER
    // =========================
    @Override
    public MemberDTO addMember(MemberRequestDTO dto) {

        // Check duplicate email
        if (memberRepository.existsByEmail(dto.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already registered"
            );
        }

        // Password is compulsory while creating member
        if (dto.getPassword() == null ||
                dto.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "Password is required"
            );
        }

        MemberEntity member = new MemberEntity();

        member.setSequenceNo(dto.getSequenceNo());
        member.setRollNo(dto.getRollNo());
        member.setUsername(dto.getUsername());
        member.setAddress(dto.getAddress());
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());
        member.setGender(dto.getGender());
        member.setBloodGroup(dto.getBloodGroup());

        // Encrypt password using BCrypt
        member.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );

        // Default role = MEMBER
        if (dto.getRole() == null ||
                dto.getRole().isBlank()) {

            member.setRole(
                    AppConstants.ROLE_MEMBER
            );

        } else {

            member.setRole(dto.getRole());
        }

        member.setDeleted(false);

        MemberEntity savedMember =
                memberRepository.save(member);

        return MemberMapper.toDTO(savedMember);
    }

    // =========================
    // GET ALL MEMBERS
    // =========================
    @Override
    public List<MemberDTO> getAllMembers() {

        return memberRepository
                .findAll()
                .stream()
                .filter(member ->
                        !Boolean.TRUE.equals(
                                member.getDeleted()
                        )
                )
                .map(MemberMapper::toDTO)
                .toList();
    }

    // =========================
    // GET MEMBER BY ID
    // =========================
    @Override
    public MemberDTO getMemberById(Long id) {

        MemberEntity member =
                memberRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found with id: " + id
                                )
                        );

        return MemberMapper.toDTO(member);
    }

    // =========================
    // GET MEMBER BY EMAIL
    // =========================
    @Override
    public MemberDTO getMemberByEmail(String email) {

        MemberEntity member =
                memberRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found"
                                )
                        );

        if (Boolean.TRUE.equals(
                member.getDeleted())) {

            throw new ResourceNotFoundException(
                    "Member account is deleted"
            );
        }

        return MemberMapper.toDTO(member);
    }

    // =========================
    // UPDATE MEMBER
    // =========================
    @Override
    public MemberDTO updateMember(
            Long id,
            MemberRequestDTO dto) {

        MemberEntity member =
                memberRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found with id: " + id
                                )
                        );

        // Check duplicate email
        if (!member.getEmail()
                .equalsIgnoreCase(dto.getEmail())
                && memberRepository
                        .existsByEmail(dto.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already registered"
            );
        }

        member.setSequenceNo(dto.getSequenceNo());
        member.setRollNo(dto.getRollNo());
        member.setUsername(dto.getUsername());
        member.setAddress(dto.getAddress());
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());
        member.setGender(dto.getGender());
        member.setBloodGroup(dto.getBloodGroup());

        // Password optional during update
        if (dto.getPassword() != null &&
                !dto.getPassword().isBlank()) {

            member.setPassword(
                    passwordEncoder.encode(
                            dto.getPassword()
                    )
            );
        }

        // Update role only when provided
        if (dto.getRole() != null &&
                !dto.getRole().isBlank()) {

            member.setRole(dto.getRole());
        }

        MemberEntity updatedMember =
                memberRepository.save(member);

        return MemberMapper.toDTO(updatedMember);
    }

    // =========================
    // SOFT DELETE MEMBER
    // =========================
    @Override
    public void deleteMember(Long id) {

        MemberEntity member =
                memberRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found with id: " + id
                                )
                        );

        member.setDeleted(true);

        memberRepository.save(member);
    }
}