package tech.bytin.api.happypath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import tech.bytin.api.TestCase;
import tech.bytin.api.jpaEntity.UserJpaEnity;

public class SnippetsIntegrationTest extends TestCase {

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
