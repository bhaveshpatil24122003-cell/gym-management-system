package com.gym.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "members")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sequenceNo; // For Sequential Roll No (e.g., 1, 2, 3...)
    private String rollNo;      // e.g., "PULSE-001"
    private String username;
    private String address;
    private String email;
    private String phone;
    private String gender;
    private String bloodGroup;
    
    private LocalDate registrationDate;
    private LocalDate expiryDate;
    
    private String subscriptionPlan; // Monthly, Quarterly, Half-Yearly, Yearly
    private Double price;
    private String paymentMode;      // Online / Cash
    private String status;           // ACTIVE / EXPIRED
    private String password;

    // Fix for database 'deleted' field constraint
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getSequenceNo() { return sequenceNo; }
    public void setSequenceNo(Integer sequenceNo) { this.sequenceNo = sequenceNo; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getSubscriptionPlan() { return subscriptionPlan; }
    public void setSubscriptionPlan(String subscriptionPlan) { this.subscriptionPlan = subscriptionPlan; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
}