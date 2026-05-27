package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.ClienteRequestDTO;
import br.com.serratec.projeto.dto.ClienteResponseDTO;
import br.com.serratec.projeto.model.Cliente;
import br.com.serratec.projeto.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para cadastro e controle de clientes da oficina")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Cadastrar cliente", description = "Salva um cliente novo, valida o endereço via ViaCEP e dispara o e-mail de boas-vindas.")
    public ResponseEntity<ClienteResponseDTO> inserir(@Valid @RequestBody ClienteRequestDTO dto) {
        
        ClienteResponseDTO novoCliente = clienteService.inserir(Cliente,dto);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do cliente")
    public ResponseEntity<ClienteResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody ClienteResponseDTO dto) {
        ClienteResponseDTO clienteAtualizado = clienteService.alterar(id, dto);
        return ResponseEntity.ok().body(clienteAtualizado);
    }

    @GetMapping
    @Operation(summary = "Listar clientes (Paginado)")
    public ResponseEntity<Page<ClienteResponseDTO>> findAll(Pageable pageable) {
        Page<ClienteResponseDTO> paginaClientes = clienteService.findAllPaged(pageable);
        
        if (paginaClientes.isEmpty()) 
            return ResponseEntity.noContent().build();
        
        
        return ResponseEntity.ok(paginaClientes);
    }
}
