package tech.bytin.api.config.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UsernamePasswordFromRequestBodyAuthenticationFilter
                extends UsernamePasswordAuthenticationFilter {

        @Autowired
        private ObjectMapper objectMapper;
        private record Details(String username, String password) { }
        private Details details;

        public UsernamePasswordFromRequestBodyAuthenticationFilter() {
                setAuthenticationSuccessHandler((req, response, e) -> {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().println("Login Successful");
                });
                setAuthenticationFailureHandler((req, response, ex) -> {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().println(ex.getMessage());
                });
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
                details = tryToGetUsernamePasswordFromRequest(request);
                return super.attemptAuthentication(request, response);
        }

        @Override
        protected String obtainUsername(HttpServletRequest request) {
                return details.username();
        }

        @Override
        protected String obtainPassword(HttpServletRequest request) {
                return details.password();
        }

        private Details getUsernamePasswordFromRequest(HttpServletRequest request)
                        throws JsonParseException, JsonMappingException, IOException {
                return objectMapper.readValue(request.getReader(), Details.class);
        }

        private Details tryToGetUsernamePasswordFromRequest(HttpServletRequest request) {
                try {
                        return getUsernamePasswordFromRequest(request);
                } catch (IOException e) {
                        e.printStackTrace();
                        return new Details(null, null);
                }
        }

}
