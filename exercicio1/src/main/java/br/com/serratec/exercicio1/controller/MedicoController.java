package br.com.serratec.exercicio1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.exercicio1.model.Medico;
import br.com.serratec.exercicio1.repository.MedicoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medico inserir(@Valid @RequestBody Medico medico){
        return repository.save(medico);
    }

    @GetMapping
    public List<Medico> listar(){
        return repository.findAll();
    }

}
