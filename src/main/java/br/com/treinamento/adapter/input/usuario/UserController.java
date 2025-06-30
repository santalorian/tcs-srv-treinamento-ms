package br.com.treinamento.adapter.input.usuario;

import br.com.treinamento.domain.entity.User;
import br.com.treinamento.domain.usecase.UserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UserController {

    private final UserUseCase useCase;

    public UserController(UserUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/salvar")
    public ResponseEntity<User> create(@Valid @RequestBody User u) {
        return ResponseEntity.ok(useCase.create(u));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(useCase.getAll());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<User> update(@PathVariable UUID id,
                                       @Valid @RequestBody User u) {
        return ResponseEntity.ok(useCase.update(id, u));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}