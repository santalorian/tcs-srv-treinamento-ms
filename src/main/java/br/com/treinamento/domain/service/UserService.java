package br.com.treinamento.domain.service;

import br.com.treinamento.domain.entity.User;
import br.com.treinamento.domain.exception.UserNotFoundException;
import br.com.treinamento.domain.usecase.UserUseCase;
import br.com.treinamento.port.output.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserUseCase {

    private final UserRepositoryPort repo;

    public UserService(UserRepositoryPort repo) {
        this.repo = repo;
    }

    @Override
    public User create(User u) {
        var now = LocalDateTime.now();
        User toSave = new User(
                UUID.randomUUID(),
                u.getUsername(), u.getPassword(), u.getDepartment(),
                now, now, u.isActive()
        );
        return repo.save(toSave);
    }

    @Override
    public User getById(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public User update(UUID id, User u) {
        User existing = getById(id);
        existing.setUsername(u.getUsername());
        existing.setPassword(u.getPassword());
        existing.setDepartment(u.getDepartment());
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setActive(u.isActive());
        return repo.save(existing);
    }

    @Override
    public void delete(UUID id) {
        getById(id);      // lança 404 se não existir
        repo.deleteById(id);
    }
}