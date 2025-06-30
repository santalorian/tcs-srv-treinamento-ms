package br.com.treinamento.domain.usecase;

import br.com.treinamento.domain.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserUseCase {
    User create(User u);
    User getById(UUID id);
    List<User> getAll();
    User update(UUID id, User u);
    void delete(UUID id);
}