package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.dto.EnderecoResponseDTO;
import br.com.serratec.projeto.model.Endereco;
import br.com.serratec.projeto.service.EnderecoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco inserir(@Valid @RequestBody Endereco endereco) {
        return service.salvar(endereco);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> listar(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/pesquisabairro")
    public ResponseEntity<Page<Endereco>> listarPorBairro(@PageableDefault(sort = "logradouro" ,direction = Direction.ASC)
        Pageable pageable, @RequestParam(defaultValue = "Mosela") String bairro){
        return ResponseEntity.ok(service.listarPorBairro(pageable, bairro));
    }

    @PutMapping("{cep}")
    public ResponseEntity<Endereco> alterar(@PathVariable String cep, @Valid @RequestBody Endereco endereco) {
        service.alterar(cep, endereco);
        var enderecoAtualizado = endereco;
        return ResponseEntity.ok().body(enderecoAtualizado);
    }

    @DeleteMapping("{cep}")
    public ResponseEntity<Void> apagar(@PathVariable String cep){
        service.apagar(cep);
        return ResponseEntity.noContent().build();
    }

}