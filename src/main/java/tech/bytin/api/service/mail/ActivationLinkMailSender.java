package tech.bytin.api.service.mail;

import org.springframework.stereotype.Service;
import core.entity.ActivationToken;

@Service
public class ActivationLinkMailSender extends MailSender {

    public void sendActivationLink(String toEmail, ActivationToken activationToken) {
        
        String activationLink = env.getProperty("web.location")
                + "/auth/register/activate?activationToken=" + activationToken;

        sendHtmMail(toEmail, "Activate Your Account", String.format("""
                <div style="width: 70%%; margin: 0 auto">
                    <p>Welcom to <em>Bytin'</em></p>
                    <p>You're almost there. Activate your account!</p>
                    <p style="margin-left: 15%%">To do so, click on the link below.</p>
                    <p style="margin-left: 30%%"><a href="%s">Activate your account</a></p>
                </div>
                """, activationLink));
    }


}
