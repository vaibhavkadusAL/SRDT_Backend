package com.srdt.contact.controller;

import com.srdt.contact.model.ContactForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = {
        "http://localhost:5173",                     // Local development
        "https://frontend-srdt-nine.vercel.app/"          //  Deployed frontend on Vercel // add link of deployed frontend i.e https://www.sairuraldevelopmenttrust.com/
})
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public ResponseMessage submitContactForm(@RequestBody @Valid ContactForm form, BindingResult result) {

        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            return new ResponseMessage("Validation failed: " + errors, false);
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("info.sairuraldevelopmenttrust@gmail.com");
            message.setSubject("New Contact Us Message from: " + form.getName());
            message.setText(
                    "Name: " + form.getName() + "\n" +
                    "Email: " + form.getEmail() + "\n" +
                    "Phone: " + form.getPhone() + "\n\n" +
                    "Message:\n" + form.getMessage()
            );
            mailSender.send(message);

            return new ResponseMessage("Message sent successfully", true);
        } catch (Exception e) {
            return new ResponseMessage("Failed to send email: " + e.getMessage(), false);
        }
    }

    // Response wrapper class
    public static class ResponseMessage {
        private String message;
        private boolean success;

        public ResponseMessage(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
