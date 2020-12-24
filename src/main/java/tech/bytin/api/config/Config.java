package tech.bytin.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import core.boundary.UserIOBoundary;
import core.gateway.UserGateway;
import core.usecase.user.UserInteractorManager;
import tech.bytin.api.repository.UserRepository;

@Configuration
public class Config implements WebMvcConfigurer {

        @Bean
        UserGateway userGateway() {
                return new UserRepository();
        }

        @Bean
        UserIOBoundary userInteractor() {
                return new UserInteractorManager(userGateway());
        } 

}
