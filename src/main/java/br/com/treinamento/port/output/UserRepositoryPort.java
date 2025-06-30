package br.com.treinamento.port.output;

import br.com.treinamento.domain.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User u);
    Optional<User> findById(UUID id);
    List<User> findAll();
    void deleteById(UUID id);
}