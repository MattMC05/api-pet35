package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.OrdemServicoResponseDTO;
import br.com.serratec.projeto.dto.OrdemServicoRequestDTO;
import br.com.serratec.projeto.service.OrdemServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
   @Operation(summary = "Cadastra um veículo vinculando-o a um ordemServico")
    public OrdemServicoResponseDTO inserir(@Valid @RequestBody OrdemServicoRequestDTO dto) {
     return osService.inserir(dto);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar OS por ID", description = "Retorna os detalhes da ordem de serviço, incluindo dados do ordemServico, veículo e o valor total.")
    public ResponseEntity<OrdemServicoResponseDTO> findById(@PathVariable Long id) {
        OrdemServicoResponseDTO ordemEncontrada = osService.findById(id);
        
        
        return ResponseEntity.ok().body(ordemEncontrada);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do ordemServico")
    public ResponseEntity<OrdemServicoResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody OrdemServicoResponseDTO dto) {
        OrdemServicoResponseDTO ordemServicoAtualizado = osService.alterar(id, dto);
        return ResponseEntity.ok().body(ordemServicoAtualizado);

    }
}