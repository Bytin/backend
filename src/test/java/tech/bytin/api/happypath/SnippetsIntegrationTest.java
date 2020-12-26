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

        final String username = "noah";

        @BeforeEach
        void init() {
                var mockJpaUser = new UserJpaEnity(1, username, "asdf", "USER");
                Mockito.when(users.findByUsername(username)).thenReturn(Optional.of(mockJpaUser));
        }

        @Test
        @WithMockUser(username = username, roles = {"USER"})
        void addEndpointTest() throws Exception {
                RequestBuilder request = post("/snippets/add").contentType("application/json")
                                .content(String.format("""
                                                {
                                                    "snippet": {
                                                        "title": "Private",
                                                        "language": "awer",
                                                        "code": "asdlfkj",
                                                        "description": "asdfklj",
                                                        "owner": {
                                                                "username": "%s"
                                                        },
                                                        "hidden": true
                                                    }
                                                }
                                                """, username));
                mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.message")
                                .value("Snippet has been successfully saved."));
        }



}
