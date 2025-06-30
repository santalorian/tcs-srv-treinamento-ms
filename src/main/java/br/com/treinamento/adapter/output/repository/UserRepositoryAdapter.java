package br.com.treinamento.adapter.output.repository;

import br.com.treinamento.adapter.output.entity.UserEntity;
import br.com.treinamento.domain.entity.User;
import br.com.treinamento.port.output.UserRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserJpaRepository jpa;
    public UserRepositoryAdapter(UserJpaRepository jpa) { this.jpa = jpa; }

    private User toDomain(UserEntity e) {
        return new User(e.getId(), e.getUsername(), e.getPassword(),
                e.getDepartment(), e.getCreatedAt(), e.getUpdatedAt(), e.isActive());
    }
    private UserEntity toEntity(User u) {
        var e = new UserEntity();
        e.setId(u.getId());
        e.setUsername(u.getUsername());
        e.setPassword(u.getPassword());
        e.setDepartment(u.getDepartment());
        e.setCreatedAt(u.getCreatedAt());
        e.setUpdatedAt(u.getUpdatedAt());
        e.setActive(u.isActive());
        return e;
    }

    @Override
    public User save(User user) {
        UserEntity entity;

        if (user.getId() != null && jpa.existsById(user.getId())) {
            // update
            entity = jpa.findById(user.getId())
                    .orElseThrow();   // nunca deve falhar
            entity.setUsername(user.getUsername());
            entity.setPassword(user.getPassword());
            entity.setDepartment(user.getDepartment());
            entity.setUpdatedAt(LocalDateTime.now());
            entity.setActive(user.isActive());
        } else {
            // create
            entity = new UserEntity();
            entity.setUsername(user.getUsername());
            entity.setPassword(user.getPassword());
            entity.setDepartment(user.getDepartment());
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            entity.setActive(user.isActive());
        }

        UserEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }
    @Override
    public List<User> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }
    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }
}