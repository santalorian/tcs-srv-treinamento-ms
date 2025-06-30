package br.com.treinamento.adapter.monitoring.entity;

import org.springframework.boot.actuate.health.Health;

public record HealthCheckServDepend(
        String url,
        Health health) {
}
