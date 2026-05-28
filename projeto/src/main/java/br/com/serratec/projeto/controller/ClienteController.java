package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.ClienteResponseDTO;
import br.com.serratec.projeto.model.Cliente;
import br.com.serratec.projeto.service.ClienteService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO inserir(@Valid @RequestBody Cliente cliente) {
        return service.inserir(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("{id}")
    public ResponseEntity<ClienteResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        if (alterar(id, cliente) != null) {
            service.alterar(id, cliente);
            var clienteAtualizado = new ClienteResponseDTO(cliente);
            return ResponseEntity.ok().body(clienteAtualizado);    
        }
        return ResponseEntity.notFound().build();
    }
}