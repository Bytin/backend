package tech.bytin.api.happypath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import core.boundary.SnippetIOBoundary;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import tech.bytin.api.config.security.SecurityConfig;
import tech.bytin.api.controller.*;
import tech.bytin.api.gateway.jpaRepo.*;
import tech.bytin.api.jpaEntity.UserJpaEnity;

@ComponentScan("tech.bytin.api")
@ContextConfiguration(classes = {SnippetIOBoundary.class, SecurityConfig.class})
@WebMvcTest(controllers = {SnippetController.class})
public class SnippetsIntegrationTest {

        @MockBean
        JpaSnippetRepository snippets;

        @MockBean
        JpaUserRepository users;

        @Autowired
        private MockMvc mvc;

        @BeforeEach
        void init() {
                var mockJpaUser = new UserJpaEnity(1, "noah", "asdf", "USER");
                Mockito.when(users.findByUsername("noah")).thenReturn(Optional.of(mockJpaUser));
        }

        @Test
        @WithMockUser(username = "noah", roles = {"USER"})
        void addEndpointTest() throws Exception {
                RequestBuilder request =
                                post("/snippets/add").contentType("application/json").content("""
                                                {
                                                    "snippet": {
                                                        "title": "Private",
                                                        "language": "awer",
                                                        "code": "asdlfkj",
                                                        "description": "asdfklj",
                                                        "owner": {
                                                                "username": "noah"
                                                        },
                                                        "hidden": true
                                                    }
                                                }
                                                """);
                mvc.perform(request).andExpect(status().isOk())
                                .andExpect(jsonPath("$.message")
                                                .value("Snippet has been successfully saved."));
        }



}
