package tech.bytin.api.config.security;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UsernamePasswordFromRequestBodyAuthenticationFilter
                extends UsernamePasswordAuthenticationFilter {

        @Autowired
        private ObjectMapper objectMapper;

        public UsernamePasswordFromRequestBodyAuthenticationFilter() {
                setAuthenticationSuccessHandler((req, response, e) -> {
                        response.setContentType("application/json");
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
                var tuple = tryToGetUsernamePasswordFromRequest(request);
                Authentication auth =
                                new UsernamePasswordAuthenticationToken(tuple.get(0), tuple.get(1));
                return getAuthenticationManager().authenticate(auth);
        }

        private List<String> getUsernamePasswordFromRequest(HttpServletRequest request)
                        throws JsonParseException, JsonMappingException, IOException {
                record Details(String username, String password) {
                };
                Details dets = objectMapper.readValue(request.getReader(), Details.class);
                return List.of(dets.username, dets.password);
        }

        private List<String> tryToGetUsernamePasswordFromRequest(HttpServletRequest request) {
                try {
                        return getUsernamePasswordFromRequest(request);
                } catch (IOException e) {
                        e.printStackTrace();
                        return List.of(null, null);
                }
        }

}
