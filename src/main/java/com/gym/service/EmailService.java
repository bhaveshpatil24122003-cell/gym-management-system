package com.gym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async; // 1. Added Async Import
import org.springframework.stereotype.Service;
import com.gym.entity.MemberEntity;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async // 2. Background process me chalega, registration slow nahi hoga!
    public void sendReceiptAndDietEmail(MemberEntity member) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("bhaveshpatil24122003@gmail.com");
            helper.setTo(member.getEmail());
            helper.setSubject("🏋️ Welcome to PulseFit Gym - Receipt & Training Program");

            String html = "<div style='font-family: Arial, sans-serif; background:#0b0f19; color:#ffffff; padding:25px; border-radius:12px; border:2px solid #f97316;'>"
                    + "<h1 style='color:#f97316; text-align:center;'>🔥 PULSEFIT GYM OFFICIAL RECEIPT</h1>"
                    + "<p>Hi <b>" + member.getUsername() + "</b>, Welcome to the family!</p>"
                    + "<hr style='border-color:#334155;'>"
                    + "<h3 style='color:#f97316;'>🧾 Payment Receipt Details</h3>"
                    + "<ul>"
                    + "<li><b>Roll No:</b> " + member.getRollNo() + "</li>"
                    + "<li><b>Plan:</b> " + member.getSubscriptionPlan() + "</li>"
                    + "<li><b>Amount Paid:</b> ₹" + member.getPrice() + " (" + member.getPaymentMode() + ")</li>"
                    + "<li><b>Valid Till:</b> " + member.getExpiryDate() + "</li>"
                    + "</ul>"
                    + "<hr style='border-color:#334155;'>"
                    + "<h3 style='color:#f97316;'>🔑 Portal Access & Auto Password</h3>"
                    + "<p>Login to Member Portal using single URL: <a href='http://localhost:8080/index.html' style='color:#f97316; font-weight:bold;'>LOGIN HERE</a></p>"
                    + "<p><b>System Password:</b> <span style='background:#f97316; color:#000; padding:4px 10px; border-radius:4px; font-weight:bold;'>" + member.getPassword() + "</span></p>"
                    + "<hr style='border-color:#334155;'>"
                    + "<h3 style='color:#f97316;'>🥗 Customized Bulking & Cutting Diet Blueprint</h3>"
                    + "<table border='1' style='width:100%; border-collapse:collapse; text-align:left; color:#fff; border-color:#334155; margin-top:10px;'>"
                    + "<tr style='background:#1e293b; color:#f97316;'><th>Meal Time</th><th>Bulking Phase (Muscle Gain) 🏋️‍♂️</th><th>Cutting Phase (Fat Loss) ✂️</th></tr>"
                    + "<tr><td>Pre-Workout</td><td>Black Coffee + 2 Bananas + Peanut Butter Toast</td><td>Black Coffee + 1 Apple</td></tr>"
                    + "<tr><td>Post-Workout</td><td>1 Scoop Whey + Creatine + 4 Eggs</td><td>1 Scoop Whey + Creatine + 3 Egg Whites</td></tr>"
                    + "<tr><td>Lunch</td><td>200g Chicken/Paneer + 2 Cup Rice + Dal</td><td>150g Chicken/Paneer + Huge Salad Bowl</td></tr>"
                    + "<tr><td>Evening Snack</td><td>Handful Nuts + Protein Shake</td><td>Green Tea + Roasted Chana</td></tr>"
                    + "<tr><td>Dinner</td><td>2 Chapati + Fish/Tofu + Veggies + Curd</td><td>100g Tofu/Paneer + Steam Veggies</td></tr>"
                    + "</table>"
                    + "</div>";

            helper.setText(html, true);
            mailSender.send(message);
            System.out.println("✅ Receipt & Diet Email Sent to: " + member.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Email Failure: " + e.getMessage());
        }
    }
}