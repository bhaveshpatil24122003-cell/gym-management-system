package com.gym.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sequenceNo;

    private String rollNo;

    private String username;

    private String address;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private String gender;

    private String bloodGroup;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String role = "MEMBER";

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
}