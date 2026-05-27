package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.VeiculoRequestDTO;
import br.com.serratec.projeto.dto.VeiculoResponseDTO;
import br.com.serratec.projeto.model.Veiculo;
import br.com.serratec.projeto.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
@Tag(name = "Veículos", description = "Endpoints para cadastro e controle da frota de veículos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    
@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um veículo vinculando-o a um cliente")
    public VeiculoResponseDTO inserir(@Valid @RequestBody VeiculoRequestDTO dto) {
        return VeiculoService.inserir(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veículo existente")
    public ResponseEntity<VeiculoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody VeiculoResponseDTO dto) {
        VeiculoResponseDTO veiculoAtualizado = veiculoService.buscar(id, dto);
        return ResponseEntity.ok().body(veiculoAtualizado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os veículos", description = "Retorna a lista completa dos veículos cadastrados trazendo junto as informações do dono.")
    public ResponseEntity<List<VeiculoResponseDTO>> findByd() {
        List<VeiculoResponseDTO> listaVeiculos = veiculoService.findById();
        
        if (listaVeiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(listaVeiculos);
    }
}