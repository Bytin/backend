package tech.bytin.api.sadpath;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import core.entity.ActivationToken;
import core.entity.User.UserRole;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import tech.bytin.api.TestCase;
import tech.bytin.api.jpaEntity.UserJpaEnity;
import tech.bytin.api.util.EntityMapper;

public class UserIntegrationTest extends TestCase {

    @Autowired
    PasswordEncoder passwordEncoder;

    final String noah = "noah";
    final String james = "james";
    final String email_noah = noah + "@gmail.com";
    final String email_james = james + "@gmail.com";
    final String password = "password@s";

    final ActivationToken activationToken = new ActivationToken(0, noah);

    @BeforeEach
    protected void init() throws Exception {
        var mockNoah = new UserJpaEnity(1, noah, email_noah, passwordEncoder.encode(password),
                UserRole.USER);
        var mockJames = new UserJpaEnity(2, noah, email_noah, passwordEncoder.encode(password),
                UserRole.UNACTIVATED);

        Mockito.when(users.findByUsername("noah")).thenReturn(Optional.of(mockNoah));
        Mockito.when(users.findByUsername("james")).thenReturn(Optional.of(mockJames));

        Mockito.when(activationTokens.findByUsername(noah))
                .thenReturn(Optional.of(EntityMapper.mapActivationTokenToJpa(activationToken)));
    }


    @Test
    void activateUserFailsWhenUserHasntRegisteredTest() throws Exception {
        var request =
                post("/user/activate").contentType("application/json").content(String.format("""
                        {
                            "username": "%s",
                            "token": "%s"
                        }
                        """, james, "laksdyblkaewrulyadsgl"));
        mvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("A token is not assigned to " + james));
    }

    @Test
    void activateUserFailsWhenTokenIsBadTest() throws Exception {
        var request =
                post("/user/activate").contentType("application/json").content(String.format("""
                        {
                            "username": "%s",
                            "token": "%s"
                        }
                        """, noah, "laksdyblkaewrulyadsgl"));
        mvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("That token is not the one assigned."));
    }

    @Test
    void loginFailsWithUnactivatedUser() throws Exception {
        String username = "james";
        var request = post("/login").contentType("application/json").content(String.format("""
                {
                        "username":"%s",
                        "password":"%s"
                }
                """, username, password));
        mvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("User is disabled")).andReturn();
    }
}
