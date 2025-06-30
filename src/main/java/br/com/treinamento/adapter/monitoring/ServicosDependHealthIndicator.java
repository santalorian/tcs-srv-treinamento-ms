package br.com.treinamento.adapter.monitoring;

import br.com.treinamento.adapter.monitoring.entity.HealthCheckServDepend;
import br.com.treinamento.adapter.monitoring.entity.HealthCheckServDependGeral;
import br.com.treinamento.config.HealthCheckEndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@ConditionalOnProperty(prefix = "management.health.servicosDependentes", name = "enabled", havingValue = "true")
@Component
public class ServicosDependHealthIndicator implements HealthIndicator{

    private final HealthCheckEndpointConfig healthCheckEndpointConfig;

    private RestTemplate restTemplate;
    @Autowired
    public ServicosDependHealthIndicator(HealthCheckEndpointConfig healthCheckEndpointConfig, RestTemplate restTemplate) {
        this.healthCheckEndpointConfig = healthCheckEndpointConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public Health health() {
        HealthCheckServDependGeral servicosDependentes = verificaSaudeServicosDependentes();
        if(!servicosDependentes.todosServicosUp()) {
            return Health.down().withDetails(Map.of("", servicosDependentes)).
                    build();
        }
        return Health.up().build();
    }

    private HealthCheckServDependGeral verificaSaudeServicosDependentes() {
        List<HealthCheckServDepend> listaHealthCheckServDepend = null;
        boolean flagTodosServicosUp = false;
        List<String> urlsDependenteList = this.healthCheckEndpointConfig.getEndpoints();

        if(!CollectionUtils.isEmpty(urlsDependenteList)) {

            listaHealthCheckServDepend =
                    urlsDependenteList.stream()
                            .map(url -> CompletableFuture.supplyAsync(() ->
                                    requisitaEndpoint(url)))
                            .map(CompletableFuture::join).toList();

            flagTodosServicosUp = listaHealthCheckServDepend.stream().allMatch(healthResponseEntity ->
                    healthResponseEntity.health().getStatus().equals(Status.UP));
        }
        return new HealthCheckServDependGeral(listaHealthCheckServDepend, flagTodosServicosUp);
    }

    private HealthCheckServDepend requisitaEndpoint(String urlEndpointHealth) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(urlEndpointHealth, Object.class);
            if(response.getStatusCode().is2xxSuccessful()){
                return new HealthCheckServDepend(urlEndpointHealth, new Health.Builder().up().build());
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
        return new HealthCheckServDepend(urlEndpointHealth, new Health.Builder().down().build());
    }
}
