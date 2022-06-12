package com.javabiz.auctionsite.service.mailservice;

import com.javabiz.auctionsite.service.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final Environment environment;

    public void sendConfirmationMailToSeller(UserModel seller){
        sendEmail(seller.getEmail(), "Congrats! You just sold something", "Someone just bough the objects of your auction.");
    }

    public void sendConfirmationMailToBuyer(UserModel buyer){
        sendEmail(buyer.getEmail(), "Congrats! You just sold something", "Someone just bough the objects of your auction.");
    }

    @Async
    void sendEmail(String to, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));

        msg.setSubject(subject);
        msg.setText(content);

        javaMailSender.send(msg);
    }
}