package tech.bytin.api.happypath;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import core.dto.UserDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import tech.bytin.api.TestCase;
import tech.bytin.api.jpaEntity.UserJpaEnity;

public class UserIntegrationTest extends TestCase {

        @Autowired
        PasswordEncoder passwordEncoder;

        String username = "noah";
        String password = "password@s";

        @BeforeEach
        protected void init() throws Exception {
                var mockJpaUser = new UserJpaEnity(1, username, passwordEncoder.encode(password),
                                "USER");
                Mockito.when(users.findByUsername("noah")).thenReturn(Optional.of(mockJpaUser));
        }

        @ParameterizedTest
        @CsvSource(value = {"bruhman, asdflkjewr", "dudeguy, sdlfkuwegh"})
        void registerUserTest(String username, String password) throws Exception {
                var request = post("/user/register").contentType("application/json")
                                .content(String.format("""
                                                {
                                                        "username": "%s",
                                                        "password": "%s"
                                                }
                                                """, username, password));
                mvc.perform(request).andExpect(status().isOk()).andExpect(
                                jsonPath("$.message").value("User '" + username + "' created."));
        }

        @Test
        void getProfileTest() throws Exception {
                var request = post("/user/profile").contentType("application/json")
                                .content(String.format("""
                                                {
                                                        "username": "%s"
                                                }
                                                """, username));
                mvc.perform(request).andExpect(status().isOk())
                                .andExpect(jsonPath("$.user").value(new UserDTO(1, username)));
        }

        @Test
        void updateUserTest() throws Exception {
                var request = post("/user/update").contentType("application/json")
                                .content(String.format("""
                                                {
                                                        "oldUsername": "%s",
                                                        "username": "roger"
                                                }
                                                """, username));
                mvc.perform(request).andExpect(status().isOk())
                                .andExpect(jsonPath("$.message").value("User info has been updated successfully."));
        }

        @Test
        void loginTest() throws Exception {
                var request = post("/login").contentType("application/json")
                                .content(String.format("""
                                                {
                                                        "username":"%s",
                                                        "password":"%s"
                                                }
                                                """, username, password));
                mvc.perform(request).andExpect(status().isOk()).andReturn();
        }
}
