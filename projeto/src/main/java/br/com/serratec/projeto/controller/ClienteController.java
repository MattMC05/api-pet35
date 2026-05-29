package br.com.serratec.projeto.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.serratec.projeto.dto.ClienteRequestDTO;
import br.com.serratec.projeto.dto.ClienteResponseDTO;
import br.com.serratec.projeto.model.Cliente;
import br.com.serratec.projeto.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gestão de clientes com integração ao ViaCEP e E-mail")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os clientes registados")
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    @Operation(summary = "Regista um novo cliente, consulta o ViaCEP e dispara e-mail")
    @PreAuthorize("hasAnyRole('ADMIN')") // Apenas pessoas autorizadas podem criar clientes
    public ResponseEntity<ClienteResponseDTO> inserir(@Valid @RequestBody Cliente dto, ClienteRequestDTO ClienteRequestDTO) {
        
        ClienteResponseDTO clienteSalvo = service.inserir(ClienteRequestDTO);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteSalvo.id())
                .toUri();
        
        // Devolve HTTP 201 (Created)
        return ResponseEntity.created(uri).body(clienteSalvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um cliente existente")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um cliente da base de dados")
    @PreAuthorize("hasRole('ADMIN')") // Exemplo: Apenas o gerente apaga registos
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Presumo que tenha um método deletar no seu ClienteService. 
        // Se não tiver, basta adicionar um repository.deleteById(id) lá!
        service.deletar(id);
        
        // Devolve HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }
}