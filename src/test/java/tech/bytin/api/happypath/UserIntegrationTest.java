package tech.bytin.api.happypath;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import core.dto.UserDTO;
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

    @ParameterizedTest
    @CsvSource(value = {"bruhman, asdflkjewr", "dudeguy, sdlfkuwegh"})
    void registerUserTest(String username, String password) throws Exception {
        var email = username + "@gmail.com";
        var request =
                post("/user/register").contentType("application/json").content(String.format("""
                        {
                                "username": "%s",
                                "email": "%s",
                                "password": "%s"
                        }
                        """, username, email, password));
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User '" + username
                        + "' created. Check your email for instructions on how to activate the account."));
    }

    @Test
    void activateUserTest() throws Exception {
        var request =
                post("/user/activate").contentType("application/json").content(String.format("""
                        {
                            "username": "%s",
                            "token": "%s"
                        }
                        """, noah, activationToken.toString()));
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User '" + noah + "' has been activated."));
    }

    @Test
    @WithMockUser(username = noah, roles = {"USER"})
    void getOwnProfileTest() throws Exception {
        var request = get("/user/profile").contentType("application/json").content(String.format("""
                {
                        "username": "%s"
                }
                """, noah));
        mvc.perform(request).andExpect(status().isOk()).andExpect(
                jsonPath("$.user").value(new UserDTO(1, noah, noah + "@gmail.com", UserRole.USER)));
    }

    @Test
    @WithMockUser(username = "anadmin", roles = {"ADMIN"})
    void getProfileTest() throws Exception {
        var request = get("/user/profile/" + noah).contentType("application/json").content(String.format("""
                {
                        "username": "%s"
                }
                """, noah));
        mvc.perform(request).andExpect(status().isOk()).andExpect(
                jsonPath("$.user").value(new UserDTO(1, noah, noah + "@gmail.com", UserRole.USER)));
    }

    @Test
    @WithMockUser(username = noah, roles = {"USER"})
    void updateUserTest() throws Exception {
        var request = put("/user/update").contentType("application/json").content(String.format("""
                {
                        "oldUsername": "%s",
                        "username": "roger"
                }
                """, noah));
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User info has been updated successfully."));
    }

    @Test
    void loginTest() throws Exception {
        var request = post("/login").contentType("application/json").content(String.format("""
                {
                        "username":"%s",
                        "password":"%s"
                }
                """, noah, password));
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$").value(new UserDTO(1, noah, email_noah, UserRole.USER)))
                .andReturn();
    }

}
