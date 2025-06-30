package br.com.treinamento.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "health-check-endpoint-custom")
@ConditionalOnProperty(prefix = "health-check-endpoint-custom", name = "endpoint")
public class HealthCheckEndpointConfig {

    private List<String> endpoints;

    public List<String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
    }
}
