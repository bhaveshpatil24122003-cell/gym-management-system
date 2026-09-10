package com.gym.service;

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

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendReceiptAndDietEmail(
            MemberEntity member,
            MembershipEntity membership,
            String plainPassword) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("bhaveshpatil24122003@gmail.com");
            helper.setTo(member.getEmail());

            helper.setSubject(
                    "Welcome to PulseFit Gym - Receipt & Training Program");

            String html =
                    "<div style='font-family:Arial;padding:25px;'>"

                    + "<h1>PulseFit Gym</h1>"

                    + "<p>Hi <b>"
                    + member.getUsername()
                    + "</b>, Welcome to the family!</p>"

                    + "<h3>Payment Receipt</h3>"

                    + "<ul>"

                    + "<li><b>Roll No:</b> "
                    + member.getRollNo()
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

                    + "<li><b>Valid Till:</b> "
                    + membership.getExpiryDate()
                    + "</li>"

                    + "</ul>"

                    + "<h3>Portal Login</h3>"

                    + "<p><b>Email:</b> "
                    + member.getEmail()
                    + "</p>"

                    + "<p><b>Temporary Password:</b> "
                    + plainPassword
                    + "</p>"

                    + "<h3>Diet Guidelines</h3>"

                    + "<p>Follow your assigned training and diet program "
                    + "according to your fitness goal.</p>"

                    + "</div>";

            helper.setText(html, true);

            mailSender.send(message);

            System.out.println(
                    "Email sent successfully to: "
                    + member.getEmail());

        } catch (Exception e) {

            System.err.println(
                    "Email sending failed: "
                    + e.getMessage());
        }
    }
}