package br.com.serratec.trabalho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.trabalho.DTO.LancamentoVendasResponseDTO;
import br.com.serratec.trabalho.model.LancamentoVendas;
import br.com.serratec.trabalho.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService service;

    @GetMapping("{id}")
    public ResponseEntity<LancamentoVendasResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoVendas inserirLancamento(@RequestBody LancamentoVendas lancamentoVendas){
        return service.inserirLancamento(lancamentoVendas);
    }


}