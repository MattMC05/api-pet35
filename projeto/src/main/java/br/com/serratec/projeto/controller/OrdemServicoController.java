package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.OrdemServicoResponseDTO;
import br.com.serratec.projeto.model.OrdemDeServico;
import br.com.serratec.projeto.service.OrdemServicoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService osService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoResponseDTO inserir(@Valid @RequestBody OrdemDeServico ordemServico) {
        return osService.inserir(ordemServico);
    }

    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> listar(){
        return ResponseEntity.ok(osService.listarTodos());
    }

    @PutMapping("{id}")
    public ResponseEntity<OrdemServicoResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody OrdemDeServico ordemServico) {
        if (alterar(id, ordemServico) != null) {
            osService.alterar(id, ordemServico);
            var ordemServicoAtualizado = new OrdemServicoResponseDTO(ordemServico);
            return ResponseEntity.ok().body(ordemServicoAtualizado);    
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        osService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}