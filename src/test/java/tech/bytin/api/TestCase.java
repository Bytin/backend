package tech.bytin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tech.bytin.api.config.security.SecurityConfig;
import tech.bytin.api.controller.UserController;
import tech.bytin.api.gateway.jpaRepo.JpaSnippetRepository;
import tech.bytin.api.gateway.jpaRepo.JpaUserRepository;

@ComponentScan("tech.bytin.api")
@ContextConfiguration(classes = {SecurityConfig.class})
@WebMvcTest(UserController.class)
public abstract class TestCase {
        @MockBean
        protected JpaUserRepository users;

        @MockBean
        protected JpaSnippetRepository snippets;// the app context, for some reason, still needs this.

        @Autowired
        protected MockMvc mvc;

}
