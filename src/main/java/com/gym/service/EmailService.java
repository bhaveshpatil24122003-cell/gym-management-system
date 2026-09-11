package com.gym.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gym.entity.MemberEntity;
import com.gym.entity.MembershipEntity;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${MAIL_USERNAME}")
    private String mailUsername;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMembershipReceipt(
            MemberEntity member,
            MembershipEntity membership) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true,
                            "UTF-8");

            helper.setFrom(mailUsername);

            helper.setTo(
                    member.getEmail());

            helper.setSubject(
                    "PulseFit Gym - Membership Receipt");

            String html =
                    "<div style='font-family:Arial;padding:25px;'>"

                    + "<h1>PulseFit Gym</h1>"

                    + "<p>Hello <b>"
                    + member.getUsername()
                    + "</b>,</p>"

                    + "<p>Your gym membership has been activated successfully.</p>"

                    + "<h3>Membership Receipt</h3>"

                    + "<ul>"

                    + "<li><b>Member ID:</b> "
                    + member.getId()
                    + "</li>"

                    + "<li><b>Roll No:</b> "
                    + member.getRollNo()
                    + "</li>"

                    + "<li><b>Name:</b> "
                    + member.getUsername()
                    + "</li>"

                    + "<li><b>Email:</b> "
                    + member.getEmail()
                    + "</li>"

                    + "<li><b>Plan:</b> "
                    + membership.getSubscriptionPlan()
                    + "</li>"

                    + "<li><b>Amount Paid:</b> ₹"
                    + membership.getPrice()
                    + "</li>"

                    + "<li><b>Payment Mode:</b> "
                    + membership.getPaymentMode()
                    + "</li>"

                    + "<li><b>Registration Date:</b> "
                    + membership.getRegistrationDate()
                    + "</li>"

                    + "<li><b>Expiry Date:</b> "
                    + membership.getExpiryDate()
                    + "</li>"

                    + "<li><b>Status:</b> "
                    + membership.getStatus()
                    + "</li>"

                    + "</ul>"

                    + "<p>Thank you for choosing PulseFit Gym.</p>"

                    + "<p><b>Stay Fit, Stay Strong!</b></p>"

                    + "</div>";

            helper.setText(html, true);

            mailSender.send(message);

            System.out.println(
                    "Membership receipt sent successfully to: "
                    + member.getEmail());

        } catch (Exception e) {

            System.err.println(
                    "Membership email failed: "
                    + e.getMessage());

            e.printStackTrace();
        }
    }
}