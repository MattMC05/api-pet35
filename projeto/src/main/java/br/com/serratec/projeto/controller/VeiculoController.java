package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.VeiculoResponseDTO;
import br.com.serratec.projeto.model.Veiculo;
import br.com.serratec.projeto.service.VeiculoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping("/paginacao")
    public Page<Veiculo> listarPorPagina(
            @PageableDefault(size = 5, page = 0, sort = "Modelo") Pageable pageable) {
        return service.listarPorPagina(pageable);
    }

    @PutMapping("{id}")
    public ResponseEntity<VeiculoResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody Veiculo veiculo) {
        service.alterar(id, veiculo);
        var veiculoAtualizado = new VeiculoResponseDTO(veiculo);
        return ResponseEntity.ok().body(veiculoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}