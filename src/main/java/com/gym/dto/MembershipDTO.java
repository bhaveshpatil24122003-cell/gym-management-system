package com.gym.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDTO {

    private Long membershipId;

    @NotBlank(message = "Subscription plan is required")
    private String subscriptionPlan;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    private LocalDate registrationDate;

    private LocalDate expiryDate;

    @NotBlank(message = "Payment mode is required")
    private String paymentMode;

    private String status;

    private Long memberId;
}