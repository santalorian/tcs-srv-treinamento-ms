package br.com.treinamento.adapter.output.repository;

import br.com.treinamento.adapter.output.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {}