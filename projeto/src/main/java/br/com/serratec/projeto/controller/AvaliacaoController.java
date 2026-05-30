package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.model.Avaliacao;
import br.com.serratec.projeto.service.AvaliacaoService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @GetMapping
    public ResponseEntity<List<Avaliacao>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Avaliacao avaliacao) {
        try {
            Avaliacao nova = service.salvar(avaliacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(nova);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {
        try {
            Avaliacao atualizada = service.atualizar(id, avaliacao);
            if (atualizada != null) {
                return ResponseEntity.ok(atualizada);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // deleta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}