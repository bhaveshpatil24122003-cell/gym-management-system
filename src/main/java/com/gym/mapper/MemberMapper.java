package com.gym.mapper;

import com.gym.dto.MemberDTO;
import com.gym.entity.MemberEntity;

public class MemberMapper {

 
    public static MemberDTO toDTO(MemberEntity member) {

        MemberDTO dto = new MemberDTO();

        dto.setId(member.getId());
        dto.setSequenceNo(member.getSequenceNo());
        dto.setRollNo(member.getRollNo());
        dto.setUsername(member.getUsername());
        dto.setAddress(member.getAddress());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setGender(member.getGender());
        dto.setBloodGroup(member.getBloodGroup());
        dto.setRole(member.getRole());
        dto.setDeleted(member.getDeleted());

        return dto;
    }
}