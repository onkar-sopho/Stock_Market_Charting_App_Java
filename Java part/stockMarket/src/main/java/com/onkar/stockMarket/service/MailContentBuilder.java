package com.onkar.stockMarket.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

// This class builds the email message which we want to send to the user
@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public String build(String message) {
        Context context = new Context();
        //Set the email message using thymeleaf's context
        context.setVariable("message", message);
        // Specify the html filename: mailTemplate.html and the context
        // so that thymeleaf can add the email message to the html file at runtime
        return templateEngine.process("mailTemplate", context);
    }
}
