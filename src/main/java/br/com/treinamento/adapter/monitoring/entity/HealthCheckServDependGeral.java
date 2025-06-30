package br.com.treinamento.adapter.monitoring.entity;

import java.util.List;

public record HealthCheckServDependGeral(
        List<HealthCheckServDepend> healthChackServDependList,
        boolean todosServicosUp) {
}
