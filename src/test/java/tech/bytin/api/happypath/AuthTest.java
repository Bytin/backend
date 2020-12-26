package tech.bytin.api.happypath;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import tech.bytin.api.config.security.SecurityConfig;
import tech.bytin.api.controller.UserController;
import tech.bytin.api.gateway.jpaRepo.JpaSnippetRepository;
import tech.bytin.api.gateway.jpaRepo.JpaUserRepository;
import tech.bytin.api.jpaEntity.UserJpaEnity;

@ComponentScan("tech.bytin.api")
@ContextConfiguration(classes = {SecurityConfig.class})
@WebMvcTest(UserController.class)
public class AuthTest {

        @MockBean
        JpaUserRepository users;

        @MockBean
        JpaSnippetRepository snippets;//the app context, for some reason, still needs this.

        @Autowired
        private MockMvc mvc;

        @Autowired
        PasswordEncoder passwordEncoder;

        String username = "noah";
        String password = "password@s";

        @BeforeEach
        void init() throws Exception {
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
