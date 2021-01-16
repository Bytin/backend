package tech.bytin.api;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tech.bytin.api.config.security.SecurityConfig;
import tech.bytin.api.gateway.jpaRepo.JpaActivationTokenRepo;
import tech.bytin.api.gateway.jpaRepo.JpaSnippetRepository;
import tech.bytin.api.gateway.jpaRepo.JpaUserRepository;

@ComponentScan("tech.bytin.api")
@ContextConfiguration(classes = {SecurityConfig.class, MailSenderAutoConfiguration.class})
@WebMvcTest()
public abstract class TestCase {
    @MockBean
    protected JpaUserRepository users;

    @MockBean
    protected JpaSnippetRepository snippets;

    @MockBean
    protected JpaActivationTokenRepo activationTokens;

    @Autowired
    protected MockMvc mvc;

    @RegisterExtension
    protected static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "testing"))
            .withPerMethodLifecycle(false);
}
