package tech.bytin.api.config.security;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import core.gateway.UserGateway;
import tech.bytin.api.util.EntityMapper;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UsernamePasswordAuthenticationFilter loginFilter() throws Exception {
        var filter = new UsernamePasswordFromRequestBodyAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/user/register", "user/profile", "/user/activate", "/hello",
                "/snippets/public/**").permitAll().anyRequest().authenticated();

        http.exceptionHandling().authenticationEntryPoint((req, res, ex) -> res
                .sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage()));

        http.logout().logoutSuccessHandler(((request, response, e) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Logout Successful");
        })).deleteCookies();

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserGateway repo = getApplicationContext().getBean("userRepository", UserGateway.class);
        auth.userDetailsService(username -> new SecurityUser(
                repo.findByUserName(username).map(EntityMapper::mapUserToJpaEntity).orElseThrow(
                        () -> new UsernameNotFoundException("Unable to find that user"))));
    }

}
