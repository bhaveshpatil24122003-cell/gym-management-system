package com.gym.service;

import java.util.List;

import com.gym.dto.MemberDTO;
import com.gym.dto.MemberRequestDTO;

public interface MemberService {

    MemberDTO addMember(MemberRequestDTO dto);

    List<MemberDTO> getAllMembers();

    MemberDTO getMemberById(Long id);

    MemberDTO getMemberByEmail(String email);

    MemberDTO updateMember(
            Long id,
            MemberRequestDTO dto
    );

    void deleteMember(Long id);
}