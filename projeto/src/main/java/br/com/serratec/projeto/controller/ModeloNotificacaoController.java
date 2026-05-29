package br.com.serratec.projeto.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import br.com.serratec.projeto.dto.ModeloNotificacaoRequestDTO;
import br.com.serratec.projeto.dto.ModeloNotificacaoResponseDTO;
import br.com.serratec.projeto.service.ModeloNotificacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notificacoes/modelos")
public class ModeloNotificacaoController {

    private final ModeloNotificacaoService service;

    public ModeloNotificacaoController(ModeloNotificacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ModeloNotificacaoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Apenas o Admin da oficina cria os modelos
    public ResponseEntity<ModeloNotificacaoResponseDTO> inserir(@Valid @RequestBody ModeloNotificacaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.inserir(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModeloNotificacaoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ModeloNotificacaoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}