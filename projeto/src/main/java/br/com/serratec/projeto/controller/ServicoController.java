package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.ServicoRequestDTO;
import br.com.serratec.projeto.dto.ServicoResponseDTO;
import br.com.serratec.projeto.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/servicos")
@Tag(name = "Serviços", description = "Endpoints para gerenciamento do catálogo de serviços da oficina")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    @Operation(summary = "Cadastrar novo serviço", description = "Adiciona um novo tipo de serviço (ex: Troca de óleo, Alinhamento) na tabela.")
    public ResponseEntity<ServicoResponseDTO> insert(@Valid @RequestBody ServicoResponseDTO dto) {
        ServicoResponseDTO novoServico = servicoService.inserir(dto);
        
        return new ResponseEntity<>(novoServico, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do serviço")
    public ResponseEntity<ServicoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ServicoResponseDTO dto) {
        ServicoResponseDTO servicoAtualizado = servicoService.update(id, dto);
        return ResponseEntity.ok().body(servicoAtualizado);
    }

    @GetMapping
    @Operation(summary = "Listar catálogo de serviços")
    public ResponseEntity<List<ServicoResponseDTO>> findAll() {
        List<ServicoResponseDTO> todosServicos = servicoService.findAll();
        
        
        return ResponseEntity.ok(todosServicos);
    }
}


