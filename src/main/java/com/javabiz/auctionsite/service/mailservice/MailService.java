package com.javabiz.auctionsite.service.mailservice;

import com.javabiz.auctionsite.service.model.Offer;
import com.javabiz.auctionsite.service.model.UserModel;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MailService {
    private static final Logger log = LogManager.getLogger(MailService.class);
    private final JavaMailSender javaMailSender;
    private final Environment environment;

    public void sendConfirmationMailToSeller(Offer offer){
        String to = offer.getAuction().getOwner().getEmail();
        String subject = "Congrats! You just sold "+offer.getAuction().getTitle();
        String content = offer.getAuction().getTitle()+" was just bought by "+offer.getOffering().getUsername()+". Soon you'll get "+offer.getPrice()+".";
        sendEmail(to, subject, content);
        log.info("Mail send to the seller with email {}.", to);
    }

    public void sendConfirmationMailToBuyer(Offer offer){
        String to = offer.getOffering().getEmail();
        String subject = "Congrats! You just bought "+offer.getAuction().getTitle();
        String content = offer.getAuction().getTitle()+" is yours! Please pay "+offer.getPrice()+" as soon as possible. "+offer.getAuction().getOwner().getUsername()+" is waiting to send you your delivery.";
        sendEmail(to, subject, content);
        log.info("Mail send to the buyer with email {}.", to);
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