package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.OrdemServicoResponseDTO;
import br.com.serratec.projeto.model.OrdemDeServico;
import br.com.serratec.projeto.service.OrdemServicoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens-servico")
@Tag(name = "Ordens de Serviço", description = "Endpoints para abertura, acompanhamento e fechamento de OS")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService osService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoResponseDTO inserir(@Valid @RequestBody OrdemDeServico ordemServico) {
        return osService.inserir(ordemServico);
    }

    /* 
    @GetMapping("/{id}")
    @Operation(summary = "Buscar OS por ID", description = "Retorna os detalhes da ordem de serviço, incluindo dados do ordemServico, veículo e o valor total.")
    public ResponseEntity<OrdemServicoResponseDTO> findById(@PathVariable Long id) {
        OrdemServicoResponseDTO ordemEncontrada = osService.findById(id);
        return ResponseEntity.ok().body(ordemEncontrada);
    }
    */

    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> listar(){
        return ResponseEntity.ok(osService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody OrdemDeServico ordemServico) {
        if (alterar(id, ordemServico) != null) {
            osService.alterar(id, ordemServico);
            var ordemServicoAtualizado = new OrdemServicoResponseDTO(ordemServico);
            return ResponseEntity.ok().body(ordemServicoAtualizado);    
        }
        return ResponseEntity.notFound().build();
    }
}