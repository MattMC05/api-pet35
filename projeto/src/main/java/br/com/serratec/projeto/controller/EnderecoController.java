package br.com.serratec.projeto.controller;

import br.com.serratec.projeto.model.Endereco;
import br.com.serratec.projeto.service.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @GetMapping
    public ResponseEntity<Page<Endereco>> listarPorBairro(@PageableDefault(sort = "logradouro" ,direction = Direction.ASC)
        Pageable pageable, @RequestParam(defaultValue = "") String bairro){
        return ResponseEntity.ok(service.listarPorBairro(pageable, bairro));
    }

}