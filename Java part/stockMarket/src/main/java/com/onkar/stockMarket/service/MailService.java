package com.onkar.stockMarket.service;

import com.onkar.stockMarket.exceptions.StockMarketException;
import com.onkar.stockMarket.model.NotificationMail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
// This class is used to send the email to the user
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationMail notificationMail) {
        // Using a lambda expression to set the details of the email using MimeMessageHelper class
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            // Mapping the mail details using the notificationMail object
            messageHelper.setFrom("stockmarketcharting@email.com");
            messageHelper.setTo(notificationMail.getRecipient());
            messageHelper.setSubject(notificationMail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationMail.getBody()));
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Activation mail has been sent !!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new StockMarketException("Exception occured while sending the mail to " + notificationMail.getRecipient(), e);
        }
    }
}
