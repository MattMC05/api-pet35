package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.ServicoResponseDTO;
import br.com.serratec.projeto.model.Servico;
import br.com.serratec.projeto.service.ServicoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoResponseDTO inserir(@Valid @RequestBody Servico servico) {
        return service.inserir(servico);
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listar(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("{id}")
    public ResponseEntity<ServicoResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody Servico servico) {
        if (alterar(id, servico) != null) {
            service.alterar(id, servico);
            var servicoAtualizado = new ServicoResponseDTO(servico);
            return ResponseEntity.ok().body(servicoAtualizado);    
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}