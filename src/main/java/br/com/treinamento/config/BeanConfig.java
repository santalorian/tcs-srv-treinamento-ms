package br.com.treinamento.config;

import br.com.treinamento.domain.service.UserService;
import br.com.treinamento.port.output.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public UserService userService(UserRepositoryPort repo) {
        return new UserService(repo);
    }
}