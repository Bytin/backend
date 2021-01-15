package tech.bytin.api.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public abstract class MailSender {

    public static final String SUBJECT_PREFIX = "[Bytin'] ";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    protected Environment env;

    protected void sendHtmMail(String toEmail, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        tryBuildingMimeMessageExceptWhenMessaginGoesWrong(mimeMessage, helper -> {
            helper.setFrom(env.getProperty("spring.mail.username"));
            helper.setTo(toEmail);
            helper.setSubject(SUBJECT_PREFIX + subject);
            helper.setText(content, true);
        });
        sendMail(mimeMessage);
    }

    private void tryBuildingMimeMessageExceptWhenMessaginGoesWrong(MimeMessage mimeMessage, MimeMessageBuilder builder) {
        try {
            builder.build(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Something went wrong while sending mail.");
        }
    }

    @Async
    private void sendMail(MimeMessage mimeMessage) {
        mailSender.send(mimeMessage);
    }

    @FunctionalInterface
    private interface MimeMessageBuilder {
        void withHelper(MimeMessageHelper helper) throws MessagingException;

        default void build(MimeMessage mimeMessage) throws MessagingException {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            withHelper(helper);
        }
    }
}
