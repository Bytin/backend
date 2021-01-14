package tech.bytin.api.service.mail;

import org.springframework.stereotype.Service;

@Service
public class ResetPasswordMailSender extends MailSender {

    public void sendPasswordResetLink(String toEmail, String resetToken) {

        String resetLink = env.getProperty("bytin.web.origin") + "/auth/reset-password?resetToken="
                + resetToken;

        sendHtmMail(toEmail, "Reset Your Password",
                String.format(
                        """
                                <div style="width: 70%; margin: 0 auto">
                                    <p>Someone requested a password reset for your account.<br> Go to this link to reset your password:</p>
                                    <p style="margin-left: 30%"><a href="%s">Reset your password</a></p>
                                    <p>If you did not request this change, simply ignore this message, your password will not reset unless you reset it.</p>
                                </div>
                                """,
                        resetLink));
    }
}
