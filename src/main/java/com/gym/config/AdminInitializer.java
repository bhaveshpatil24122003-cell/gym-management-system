package com.gym.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gym.entity.MemberEntity;
import com.gym.repository.MemberRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_NAME}")
    private String adminName;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public AdminInitializer(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder) {

        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (memberRepository.findByEmail(adminEmail).isEmpty()) {

            MemberEntity admin = new MemberEntity();

            admin.setSequenceNo(1);
            admin.setRollNo("ADMIN001");
            admin.setUsername(adminName);
            admin.setAddress("PulseFit Gym");
            admin.setEmail(adminEmail);
            admin.setPhone("8767754615");
            admin.setGender("Male");
            admin.setBloodGroup("O+");

            admin.setPassword(
                    passwordEncoder.encode(adminPassword));

            admin.setRole("ADMIN");
            admin.setDeleted(false);

            memberRepository.save(admin);

            System.out.println(
                    "Default ADMIN created successfully");
        }
    }
}