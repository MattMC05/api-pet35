package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.VeiculoResponseDTO;
import br.com.serratec.projeto.model.Veiculo;
import br.com.serratec.projeto.service.VeiculoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoResponseDTO inserir(@Valid @RequestBody Veiculo veiculo) {
        return service.inserir(veiculo);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listar(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("{id}")
    public ResponseEntity<VeiculoResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody Veiculo veiculo) {
        if (alterar(id, veiculo) != null) {
            service.alterar(id, veiculo);
            var veiculoAtualizado = new VeiculoResponseDTO(veiculo);
            return ResponseEntity.ok().body(veiculoAtualizado);    
        }
        return ResponseEntity.notFound().build();
    }
}