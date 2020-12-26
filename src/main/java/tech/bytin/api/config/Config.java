package tech.bytin.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import core.boundary.SnippetIOBoundary;
import core.boundary.UserIOBoundary;
import core.gateway.SnippetGateway;
import core.gateway.UserGateway;
import core.usecase.snippet.SnippetInteractorManager;
import core.usecase.user.UserInteractorManager;
import tech.bytin.api.gateway.SnippetRepository;
import tech.bytin.api.gateway.UserRepository;
import tech.bytin.api.gateway.jpaRepo.JpaSnippetRepository;
import tech.bytin.api.gateway.jpaRepo.JpaUserRepository;

@Configuration
public class Config {

        @Autowired
        JpaUserRepository userRepo;

        @Autowired
        JpaSnippetRepository snippetRepo;

        @Bean
        UserGateway userGateway() {
                return new UserRepository(userRepo);
        }

        @Bean
        UserIOBoundary userInteractor() {
                return new UserInteractorManager(userGateway());
        }

        @Bean
        SnippetGateway snippetGateway() {
                return new SnippetRepository(snippetRepo);
        }

        @Bean
        SnippetIOBoundary snippetInteractor() {
                return new SnippetInteractorManager(snippetGateway(), userGateway());
        }

}
