package com.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long id;
    private Integer sequenceNo;
    private String rollNo;
    private String username;
    private String address;
    private String email;
    private String phone;
    private String gender;
    private String bloodGroup;
    private String role;
    private Boolean deleted;
}