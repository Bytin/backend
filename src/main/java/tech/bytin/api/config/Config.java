package tech.bytin.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import core.boundary.SnippetIOBoundary;
import core.boundary.UserIOBoundary;
import core.gateway.ActivationTokenGateway;
import core.gateway.SnippetGateway;
import core.gateway.UserGateway;
import core.usecase.snippet.SnippetInteractorManager;
import core.usecase.user.UserInteractorManager;

@Configuration
public class Config {

        @Autowired
        UserGateway userGateway;

        @Autowired
        SnippetGateway snippetGateway;

        @Autowired
        ActivationTokenGateway tokenGateway;

        @Bean
        UserIOBoundary userInteractor() {
                return new UserInteractorManager(userGateway, tokenGateway);
        }

        @Bean
        SnippetIOBoundary snippetInteractor() {
                return new SnippetInteractorManager(snippetGateway, userGateway);
        }

}
