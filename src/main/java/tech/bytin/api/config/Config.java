package tech.bytin.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import core.boundary.SnippetIOBoundary;
import core.boundary.UserIOBoundary;
import core.gateway.SnippetGateway;
import core.gateway.UserGateway;
import core.usecase.snippet.SnippetInteractorManager;
import core.usecase.user.UserInteractorManager;
import tech.bytin.api.repository.SnippetRepository;
import tech.bytin.api.repository.UserRepository;

@Configuration
public class Config {

        @Bean
        UserGateway userGateway() {
                return new UserRepository();
        }

        @Bean
        UserIOBoundary userInteractor() {
                return new UserInteractorManager(userGateway());
        }

        @Bean
        SnippetGateway snippetGateway() {
                return new SnippetRepository();
        }

        @Bean
        SnippetIOBoundary snippetInteractor() {
                return new SnippetInteractorManager(snippetGateway(), userGateway());
        }

}
