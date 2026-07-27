package com.gym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gym.entity.MemberEntity;
import com.gym.repository.MemberRepository;
import com.gym.service.EmailService;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired(required = false)
    private EmailService emailService;

    // 1. Unified Authentication Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> creds) {
        String username = creds.get("username");
        String password = creds.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password required!"));
        }

        // Hardcoded Admin Auth
        if ("bhavesh".equalsIgnoreCase(username) && "1234".equals(password)) {
            return ResponseEntity.ok(Map.of("role", "ADMIN", "message", "Welcome Admin!"));
        }

        // Safe Member Login Check
        Optional<MemberEntity> memberOpt = memberRepository.findAll().stream()
                .filter(m -> (m.getEmail() != null && m.getEmail().equalsIgnoreCase(username)) 
                          || (m.getRollNo() != null && m.getRollNo().equalsIgnoreCase(username)))
                .filter(m -> m.getPassword() != null && m.getPassword().equals(password))
                .findFirst();

        if (memberOpt.isPresent()) {
            return ResponseEntity.ok(Map.of("role", "USER", "member", memberOpt.get()));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid Credentials!"));
    }

    // 2. GET All Members (Automated Sequence Alignment)
    @GetMapping("/members")
    public List<MemberEntity> getAllMembers() {
        reorderSequences();
        return memberRepository.findAllByOrderBySequenceNoAsc();
    }

    // 3. POST - Create Member
    @PostMapping("/members")
    public ResponseEntity<?> createMember(@RequestBody MemberEntity member) {
        try {
            // Validation: Check duplicate email
            if (member.getEmail() != null && memberRepository.findByEmail(member.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already registered!"));
            }

            // Auto Sequence & Roll Number Generation
            List<MemberEntity> allMembers = memberRepository.findAllByOrderBySequenceNoAsc();
            int nextSeq = allMembers.size() + 1;
            member.setSequenceNo(nextSeq);
            member.setRollNo(String.format("PULSE-%03d", nextSeq));

            // Auto Password if empty
            if (member.getPassword() == null || member.getPassword().trim().isEmpty()) {
                String autoPass = "Fit#" + (int)(Math.random() * 9000 + 1000);
                member.setPassword(autoPass);
            }

            member.setRegistrationDate(LocalDate.now());
            member.setStatus("ACTIVE");

            // Compute Expiry
            calculateExpiry(member);

            // Save Member
            MemberEntity saved = memberRepository.save(member);
            
            // Safe Dispatch Mail (Prevents crash if SMTP is not working)
            if (emailService != null) {
                try {
                    emailService.sendReceiptAndDietEmail(saved);
                } catch (Exception e) {
                    System.err.println("Email fail ho gaya par registration complete ho chuki hai: " + e.getMessage());
                }
            }

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Server Error: " + e.getMessage()));
        }
    }

    // 4. PUT - Update Member
    @PutMapping("/members/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody MemberEntity details) {
        return memberRepository.findById(id).map(m -> {
            m.setUsername(details.getUsername());
            m.setPhone(details.getPhone());
            m.setAddress(details.getAddress());
            m.setGender(details.getGender());
            m.setBloodGroup(details.getBloodGroup());
            m.setSubscriptionPlan(details.getSubscriptionPlan());
            m.setPrice(details.getPrice());
            m.setPaymentMode(details.getPaymentMode());
            calculateExpiry(m);
            MemberEntity updated = memberRepository.save(m);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE - Permanent Delete & Re-sequence Roll Numbers
    @DeleteMapping("/members/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        if (!memberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        memberRepository.deleteById(id);
        reorderSequences();
        return ResponseEntity.ok(Map.of("message", "Member permanently deleted and roll numbers updated!"));
    }

    // 6. Admin Analytics Stats
    @GetMapping("/admin/stats")
    public ResponseEntity<?> getStats() {
        List<MemberEntity> list = memberRepository.findAll();
        long total = list.size();
        long expired = list.stream().filter(m -> "EXPIRED".equalsIgnoreCase(m.getStatus()) || (m.getExpiryDate() != null && m.getExpiryDate().isBefore(LocalDate.now()))).count();
        double revenue = list.stream().mapToDouble(m -> m.getPrice() != null ? m.getPrice() : 0.0).sum();

        return ResponseEntity.ok(Map.of("totalUsers", total, "expiredUsers", expired, "totalRevenue", revenue));
    }

    // Helper: Calculate Subscription Expiry Date
    private void calculateExpiry(MemberEntity member) {
        if ("Monthly".equalsIgnoreCase(member.getSubscriptionPlan())) member.setExpiryDate(LocalDate.now().plusMonths(1));
        else if ("Quarterly".equalsIgnoreCase(member.getSubscriptionPlan())) member.setExpiryDate(LocalDate.now().plusMonths(3));
        else if ("Half-Yearly".equalsIgnoreCase(member.getSubscriptionPlan())) member.setExpiryDate(LocalDate.now().plusMonths(6));
        else if ("Yearly".equalsIgnoreCase(member.getSubscriptionPlan())) member.setExpiryDate(LocalDate.now().plusYears(1));
    }

    // Helper: Re-align Sequences and Roll Nos
    private void reorderSequences() {
        List<MemberEntity> list = memberRepository.findAll();
        list.sort(Comparator.comparing(MemberEntity::getId));
        for (int i = 0; i < list.size(); i++) {
            MemberEntity m = list.get(i);
            m.setSequenceNo(i + 1);
            m.setRollNo(String.format("PULSE-%03d", i + 1));
            memberRepository.save(m);
        }
    }
}