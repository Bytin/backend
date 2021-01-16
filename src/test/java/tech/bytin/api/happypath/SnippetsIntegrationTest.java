package tech.bytin.api.happypath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import core.entity.User.UserRole;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import tech.bytin.api.TestCase;
import tech.bytin.api.jpaEntity.UserJpaEnity;

public class SnippetsIntegrationTest extends TestCase {

        final String username = "noah";
        final String email = username + "@gmail.com";

        @BeforeEach
        void init() {
                var mockJpaUser = new UserJpaEnity(1, username, email, "asdf", UserRole.USER);
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

        @Test
        @WithMockUser(username = username, roles = {"USER"})
        void deleteTest() throws Exception{
            var request = delete("/snippets/delete/105");
            mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Snippet was deleted successfully."));
        }

}
